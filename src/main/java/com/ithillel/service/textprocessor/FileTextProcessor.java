package com.ithillel.service.textprocessor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class FileTextProcessor implements TextProcessor {

    private String path =
            String.format("src%smain%sjava%scom%sithillel%sservice",
                    File.separator, File.separator, File.separator, File.separator, File.separator);

    public void save(String key, final String text) {
        Path resolve = Paths.get(path).resolve(key);
        System.out.println(resolve);
        try {
            Files.write(resolve, text.getBytes(StandardCharsets.UTF_8), Files.exists(resolve)
                    ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isEmpty(String key) {
        return Objects.isNull(key) || key.isEmpty();
    }

    public String getByKey(String key) {
        if (isEmpty(key)) {
            throw new IllegalArgumentException("key must be a set");
        }
        try {
            Path resolve = Paths.get(path).resolve(key);
            if (!Files.exists(resolve)) {
                throw new IllegalStateException("file not found");
            }
            byte[] bytes = Files.readAllBytes(resolve);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("file not found");
        }
    }
}
