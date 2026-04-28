package com.cricketSeries.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class SqlLoader {

    private final Map<String, String> queries = new HashMap<>();

    public SqlLoader() throws IOException {
        loadSql("sql/series.sql");
    }

    private void loadSql(String path) throws IOException {
        InputStream is = new ClassPathResource(path).getInputStream();
        String content = new String(is.readAllBytes());

        String[] parts = content.split("-- name:");
        for (String part : parts) {
            if (part.trim().isEmpty()) continue;

            String[] lines = part.split("\n", 2);
            String name = lines[0].trim();
            String query = lines[1].trim();

            queries.put(name, query);
        }
    }

    public String get(String name) {
        return queries.get(name);
    }
}
