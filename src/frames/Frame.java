package frames;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import frames.constraint.Constraint;
import frames.constraint.ContainsConstraint;
import frames.constraint.RangeConstraint;
import frames.constraint.TypeConstraint;
import frames.util.ClassTypeAdapterFactory;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class Frame implements Cloneable {
    private String name;
	protected FrameRef parent;
    protected Map<String, Slot> slots;
	
	public Frame(String name) {
	    this.name = name;
	    this.slots = new HashMap<>();
	}
	
	public Frame(String name, GenericFrame parent) {
        this(name);
        this.parent = parent.ref();
    }

    public Frame(Frame frame) {
	    this.copy(frame);
    }

    protected void copy(Frame frame) {
        this.name = frame.name;
        this.parent = frame.parent;
        this.slots = new HashMap<>();

        for (String slot : frame.slots.keySet()) {
            this.slots.put(slot, new Slot(this, frame.slots.get(slot)));
        }
    }

    public String name() {return this.name;}
	public GenericFrame parent() {
	    return this.parent != null && this.parent.retrieve() != null? (GenericFrame) this.parent.retrieve() : null;
	}

	public Set<String> slots() {
	    Set<String> slots = new HashSet<>();
	    GenericFrame parent = this.parent();

	    slots.addAll(this.slots.keySet());

	    if (parent != null) {
	        slots.addAll(parent.slots());
        }

        return slots;
	}

	public void setName(String name) {this.name = name;}
    public void setParent(GenericFrame parent) {this.parent = parent.ref();}
    public void setParent(FrameRef ref) {this.parent = ref;}

    public boolean isA(String type) {
	    return name().equals(type) || (parent() != null && parent().isA(type));
    }

    public boolean isA(GenericFrame type) {return this.isA(type.name());}

    public boolean contains(String key) {
	    return slots.containsKey(key);
    }
    
    protected Slot find(String key) throws NoSuchElementException {
        if (this.contains(key))
	        return slots.get(key);
	    else if (parent() != null)
	        return parent().find(key);
	    else
	        throw new NoSuchElementException("Slot '" + key
                    + "' not found on frame " + name());
    }

	public Object get(String key) throws NoSuchElementException {
	    return this.find(key).getValue();
	}

	public <T> T get(String key, Class<T> type) throws ClassCastException {
        Object value = this.get(key);

	    try {
            return type.cast(value);
        } catch (ClassCastException e) {
            throw new ClassCastException("Slot '" + key + "' is of type "
                    + value.getClass().getSimpleName() + ". "
                    + type.getSimpleName() + " given.");
        }
	}

	public <T> void set(String key, T value) {
	    try {
	        Slot slot = this.find(key);
	        slot.setValue(value);

	        slots.put(key, slot);
        } catch (NoSuchElementException e) {
            slots.put(key, new Slot(this, value));
        }
	}

	public void ifAdded(String key, Consumer<Object> if_added) {
	    Slot slot;

	    try {
	        slot = this.find(key);
        } catch (NoSuchElementException e) {
	        slot = new Slot(this);
        }

	    slot.setIfAdded(if_added);
	    slots.put(key, slot);
    }

    public void ifNeeded(String key, Function<Frame, Object> if_needed) {
		Slot slot;

		try {
			slot = this.find(key);
		} catch (NoSuchElementException e) {
			slot = new Slot(this);
		}

		slot.setIfNeeded(if_needed);
		slots.put(key, slot);
    }

	public void addConstraint(String key, Constraint constraint) {
	    Slot slot;

	    try {
	        slot = this.find(key);
        } catch (NoSuchElementException e) {
	        slot = new Slot(this);
        }

        slot.addConstraint(constraint);
	    slots.put(key, slot);
    }

    public FrameRef ref() {return new FrameRef(this);}

    public String toJson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapterFactory(new ClassTypeAdapterFactory());
        builder.serializeNulls();

	    return builder.create().toJson(this);
    }

    public static Frame fromJson(String json) {
	    Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        JsonObject frameJson = parser.parse(json).getAsJsonObject();
        Frame frame = new GenericFrame(frameJson.get("name").getAsString());

        JsonElement parent = frameJson.get("parent");
        if (!parent.isJsonNull()) {
            frame.setParent(gson.fromJson(parent, FrameRef.class));
        }

        JsonObject slots = frameJson.get("slots").getAsJsonObject();
        for (Map.Entry<String, JsonElement> slot : slots.entrySet()) {
            String key = slot.getKey();
            JsonObject filler = slot.getValue().getAsJsonObject();

            Predicate<JsonElement> exists = el -> el != null && !el.isJsonNull();

            JsonElement value = filler.get("value");
            JsonElement if_added = filler.get("if-added");
            JsonElement if_needed = filler.get("if-needed");
            JsonElement constraints = filler.get("constraints");

            if (exists.test(value))
                frame.set(key, gson.fromJson(filler.get("value"), Object.class));

            if (exists.test(if_added))
                frame.ifAdded(key, KnowledgeBase.retrieveIfAdded(if_added.getAsString()));

            if (exists.test(if_needed))
                frame.ifNeeded(key, KnowledgeBase.retrieveIfNeeded(if_needed.getAsString()));

            if (exists.test(constraints)) {
                for (JsonElement constraint : filler.get("constraints").getAsJsonArray()) {
                    JsonObject constraintObj = constraint.getAsJsonObject();
                    String constrType = constraintObj.get("type").getAsString();
                    Constraint constr;
                    try {
                        switch (constrType) {
                            case "type":
                                String className = constraintObj.get("class-name").getAsString();
                                constr = new TypeConstraint(Class.forName(className));
                                break;

                            case "contains":
                                JsonArray accepts = constraintObj.get("accepts").getAsJsonArray();
                                constr = new ContainsConstraint(gson.fromJson(accepts, ArrayList.class));
                                break;

                            case "range":
                                Comparable lo = (Comparable) gson.fromJson(constraintObj.get("lo"), Object.class);
                                Comparable hi = (Comparable) gson.fromJson(constraintObj.get("hi"), Object.class);
                                JsonArray bounds = constraintObj.get("bounds").getAsJsonArray();
                                String inclusivity = bounds.get(0).getAsString() + bounds.get(1).getAsString();

                                constr = new RangeConstraint(lo, hi, inclusivity);
                                break;
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return frame;
    }
}
