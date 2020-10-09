package com.ithillel.service.textprocessor;

public interface TextProcessor {
    void save(String key, String text);

    String getByKey(String key);
}
