package com.example.gachitayo.vo;

import java.util.List;

public class SearchResult {
    List<PlaceInfo> documents;

    public List<PlaceInfo> getDocuments() {
        return documents;
    }

    public void setDocuments(List<PlaceInfo> documents) {
        this.documents = documents;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "documents=" + documents.toString() +
                '}';
    }
}

