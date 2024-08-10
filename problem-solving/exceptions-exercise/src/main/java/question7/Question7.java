package question7;

import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Question7 {

    // the following program uses the gson library to read and parse
    // a json file. Json is a pretty well known human-readable format
    // used to both store and send information.
    // the file is located under src/main/java/resources
    //
    // json is very structured format, allowing users to define
    // arrays, and maps of data.
    // this program attempts to read an array of integers from the file.
    // but it has problems...

    public static void main(String[] args) throws IOException {
        List<Integer> list = readIntListFromResource("/q7file.json");
        System.out.println(list);
    }

    static List<Integer> readIntListFromResource(String path) throws IOException {
        try (InputStream stream = Question7.class.getResourceAsStream(path)) {
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
