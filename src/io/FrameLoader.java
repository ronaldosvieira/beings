package io;

import frames.Frame;
import frames.KnowledgeBase;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FrameLoader {
    public static void load(String path) throws FileNotFoundException {
        List<File> files = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    files.add(filePath.toFile());
                }
            });
        } catch (IOException e) {
            throw new FileNotFoundException("Folder " + path + " not found");
        }

        for (File file : files) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String json = br.lines().reduce((s, s2) -> s + s2).get();

                KnowledgeBase.register(Frame.fromJson(json));
            } catch (IOException e) {
                throw new IllegalArgumentException("Couldn't open file " + path);
            }
        }
    }
}
