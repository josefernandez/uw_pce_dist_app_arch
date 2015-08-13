package com.nozama.search.impl;

import java.io.IOException;
import java.util.*;

import com.nozama.search.Searcher;

public class SearcherBuilder {
	private Set<String> stopWords = new HashSet<>();
	private Map<String, Set<String>> synMap = new HashMap<>();
	private boolean useLowercase = false;
	private boolean useFolding = false;
	private boolean useStemming = true;
	
	public SearcherBuilder addStopWord(String word) {
		stopWords.add(word);
		return this;
	}
	
	public SearcherBuilder addSynonym(String word, Set<String> synonyms) {
		synMap.put(word, synonyms);
		return this;
	}
	
	public SearcherBuilder useLowercase(boolean use) {
		this.useLowercase = use;
		return this;
	}
	
	public SearcherBuilder useFolding(boolean use) {
		this.useFolding = use;
		return this;
	}
	
	public SearcherBuilder useStemming(boolean use) {
		this.useStemming = use;
		return this;
	}

	public Searcher build() throws IOException {
		return new SearcherImpl(stopWords, synMap, useLowercase, useFolding, useStemming);
	}

}
