package CampusLibrarySystem.util;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CSVUtil {

    public static List<String[]> read(String filePath) {
        List<String[]> data = new ArrayList<>();
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",", -1); // -1 to include empty strings
                data.add(fields);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void write(String filePath, List<String[]> data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
            for (String[] row : data) {
                writer.write(String.join(",", escapeSpecialChars(row)));
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] escapeSpecialChars(String[] row) {
        String[] escaped = new String[row.length];
        for (int i = 0; i < row.length; i++) {
            String value = row[i];
            if (value.contains(",") || value.contains("\"")) {
                value = "\"" + value.replace("\"", "\"\"") + "\"";
            }
            escaped[i] = value;
        }
        return escaped;
    }
}