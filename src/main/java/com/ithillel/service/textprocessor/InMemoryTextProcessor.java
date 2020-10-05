package com.ithillel.service.textprocessor;

import com.ithillel.service.annotation.CustomBean;
import com.ithillel.service.storage.Storage;
import java.util.Objects;

@CustomBean
public class InMemoryTextProcessor implements TextProcessor {

    private final Storage storage;

    public InMemoryTextProcessor(Storage storage) {
        this.storage = storage;
    }

    public void save(String key, final String text) {
        if (isEmpty(key)) {
            throw new IllegalArgumentException("key must be a set");
        }
        if (isEmpty(text)) {
            return;
        }
        storage.compute(key, (k, value) -> (value == null ? text : "\n" + text));
    }

    private boolean isEmpty(String key) {
        return Objects.isNull(key) || key.isEmpty();
    }

    public String getByKey(String key) {
        if (isEmpty(key)) {
            throw new IllegalArgumentException("key must be a set");
        }
        String text = storage.get(key);
        if (text == null) {
            throw new IllegalStateException("key not found");
        }
        return text;
    }
}
