package frames;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class KnowledgeBase {
    private static HashMap<String, Frame> frames = new HashMap<>();
    private static HashMap<String, Consumer<Object>> ifAdded = new HashMap<>();
    private static HashMap<String, Supplier<Object>> ifNeeded = new HashMap<>();

    public KnowledgeBase() {
        frames = new HashMap<>();
    }

    public static void register(Frame frame) {frames.put(frame.name(), frame);}
    public static void register(String name, Consumer<Object> script) {ifAdded.put(name, script);}
    public static void register(String name, Supplier<Object> script) {ifNeeded.put(name, script);}

    public static Frame retrieveFrame(String name) {return frames.get(name);}
    public static Consumer<Object> retrieveIfAdded(String name) {return ifAdded.get(name);}
    public static Supplier<Object> retrieveIfNeeded(String name) {return ifNeeded.get(name);}
}
