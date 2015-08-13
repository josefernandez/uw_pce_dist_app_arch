package com.nozama.search.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import com.nozama.products.Product;
import com.nozama.search.Searcher;

class SearcherImpl implements Searcher {
	private final Version v = Version.LUCENE_4_10_4;
	private Directory ramDir = new RAMDirectory();
	
	private IndexWriter writer;
	private QueryParser parser;
	private IndexSearcher searcher;
			
	SearcherImpl (
		Set<String> stopWords,
		Map<String, Set<String>> synonyms,
		boolean useLowercase,
		boolean useFolding,
		boolean useStemming
	) throws IOException {
		Analyzer indexAnalyzer = new ProductAnalyzer(stopWords, synonyms, useLowercase, useFolding, useStemming);
		Analyzer searchAnalyzer = new ProductAnalyzer(stopWords, synonyms, useLowercase, useFolding, useStemming);
		
		IndexWriterConfig conf = new IndexWriterConfig(v, indexAnalyzer);
		writer = new IndexWriter(ramDir, conf);
		parser = new QueryParser("name", searchAnalyzer);
	}
	
	@Override
	public void index(Collection<Product> products) throws IOException {
		for (Product p : products) {
			index(p);
		}

		writer.close();
	}

	@SuppressWarnings("deprecation")
	private void index(Product p) throws IOException {
		Document doc = new Document();
		doc.add(new StoredField("id", p.getId()));
		doc.add(new Field("name", p.getName(), Field.Store.YES, Field.Index.ANALYZED));
		writer.addDocument(doc);
	}
	
	private IndexSearcher getSearcher() throws IOException {
		if (searcher == null) {
			searcher = new IndexSearcher(DirectoryReader.open(ramDir));
		}

		return searcher;
	}

	@Override
	public List<Product> doQuery(String query) throws IOException {
		Query q = parser.createBooleanQuery("name", query);
		
		List<Product> products = new ArrayList<>();
		
		if (q != null) {
			TopDocs hits = getSearcher().search(q, 100);
			
			for (ScoreDoc scoreDoc : hits.scoreDocs) {
				Document doc = searcher.doc(scoreDoc.doc);
				products.add(new Product(doc.getField("id").numericValue().intValue(), doc.get("name")));
			}
		}
		
		return products;
	}

}
