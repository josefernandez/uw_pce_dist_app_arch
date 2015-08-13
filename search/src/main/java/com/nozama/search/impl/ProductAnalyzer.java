package com.nozama.search.impl;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.icu.ICUFoldingFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.synonym.SynonymFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.util.CharsRef;

public class ProductAnalyzer extends Analyzer {
	private CharArraySet stops;
	private SynonymMap synMap;
	private boolean useLowercase;
	private boolean useFolding;
	private boolean useStemming;
	
	public ProductAnalyzer(
			Set<String> stopWords,
			Map<String, Set<String>> synonyms,
			boolean useLowercase,
			boolean useFolding,
			boolean useStemming) {
		
		this.stops = new CharArraySet(stopWords, true);

		try {
			this.synMap = buildSynonyms(synonyms);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		this.useLowercase = useLowercase;
		this.useFolding = useFolding;
		this.useStemming = useStemming;
	}
	
	private SynonymMap buildSynonyms(Map<String, Set<String>> synonyms) throws IOException {
		SynonymMap.Builder b = new SynonymMap.Builder(true);
		
		for (Map.Entry<String, Set<String>> e : synonyms.entrySet()) {
			for (String syn : e.getValue()) {
				b.add(new CharsRef(e.getKey()), new CharsRef(syn), true);
			}
		}
		
		return b.build();
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
		Tokenizer tokenizer = new StandardTokenizer(reader);

		TokenStream filter = tokenizer;

		if (!stops.isEmpty())
			filter = new StopFilter(filter, stops);
		if (synMap.fst != null)
			filter = new SynonymFilter(filter, synMap, true);
		if (useLowercase)
			filter = new LowerCaseFilter(filter);
		if (useFolding)
			filter = new ICUFoldingFilter(filter);
		if (useStemming)
			filter = new PorterStemFilter(filter);
		
		return new TokenStreamComponents(tokenizer, filter);
	}

}
