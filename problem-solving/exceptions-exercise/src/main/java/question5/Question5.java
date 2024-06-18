package question5;

import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Question5 {

    public static void main(String[] args) throws IOException {
        List<Integer> list = readIntListFromResource("/q5file.json");
        System.out.println(list);
    }

    static List<Integer> readIntListFromResource(String path) throws IOException {
        try (InputStream stream = Question5.class.getResourceAsStream(path)) {
            return readIntListFromStream(stream);
        }
    }

    static List<Integer> readIntListFromStream(InputStream stream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
             JsonReader jsonReader = new JsonReader(reader)) {
            return readIntListFromReader(jsonReader);
        }
    }

    static List<Integer> readIntListFromReader(JsonReader reader) throws IOException {
        List<Integer> list = new ArrayList<>();

        reader.beginArray();
        while (reader.hasNext()) {
            int value = reader.nextInt();
            list.add(value);
        }
        reader.endArray();

        return list;
    }
}
