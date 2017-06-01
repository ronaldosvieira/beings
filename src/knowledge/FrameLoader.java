package knowledge;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import model.Frame;
import model.GenericFrame;
import model.InstanceFrame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KnowledgeBase {
    private HashMap<String, Frame> frames;

    public KnowledgeBase() {
        this.frames = new HashMap<>();
    }

    public void load(String path) throws FileNotFoundException {
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

        List<JsonObject> generics = new ArrayList<>();
        List<JsonObject> instances = new ArrayList<>();

        for (File file : files) {
            try {
                JsonObject object = Json.parse(new FileReader(file)).asObject();
                String type = object.getString("type", "instance");

                if (type.equals("generic")) generics.add(object);
                else if (type.equals("instance")) instances.add(object);
            } catch (IOException e) {
                throw new IllegalArgumentException("Wrong syntax on file " + path);
            }
        }

        for (JsonObject object : generics) {
            String name = object.get("name").asString();

            frames.put(name, new GenericFrame(name));
        }

        for (JsonObject object : generics) {
            String name = object.get("name").asString();
            JsonValue parent = object.get("parent");

            if (parent.isString()) {
                Frame frame = frames.get(name);

                frame.setParent((GenericFrame) frames.get(parent.asString()));

                frames.put(name, frame);
            }
        }

        for (JsonObject object : instances) {
            String name = object.get("name").asString();
            JsonValue parent = object.get("parent");

            if (!parent.isString())
                throw new IllegalArgumentException("Instance frame " + name
                        + " has no parent");

            Frame frame = new InstanceFrame(name,
                    (GenericFrame) frames.get(parent.asString()));
        }
    }

    public Frame retrieve(String key) {
        return frames.get(key);
    }

    public void put(String key, Frame frame) {
        frames.put(key, frame);
    }
}
