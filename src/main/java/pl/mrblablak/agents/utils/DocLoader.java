package pl.mrblablak.agents.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class DocLoader {
    public static String search(String query) {
        try {
            return Files.walk(Path.of("src/main/resources/docs"))
                    .filter(Files::isRegularFile)
                    .map(path -> {
                        try {
                            String content = Files.readString(path);
                            if (content.toLowerCase().contains(query.toLowerCase())) {
                                return "Match found in " + path.getFileName() + ":\n" + content;
                            }
                        } catch (Exception ignored) {}
                        return "";
                    })
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.joining("\n\n"));
        } catch (Exception e) {
            return "Error searching docs: " + e.getMessage();
        }
    }
}
