package frames.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.function.Function;

public class FunctionTypeAdapter extends TypeAdapter<Function> {
    @Override
    public void write(JsonWriter jsonWriter, Function func) throws IOException {
        if (func == null){
            jsonWriter.nullValue();
            return;
        }

        jsonWriter.value("lambda");
    }

    @Override
    public Function read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        Function func = null;
        /*try {
            //func = Class.forName(jsonReader.nextString());
        } catch (ClassNotFoundException exception) {
            throw new IOException(exception);
        }*/
        return func;
    }
}