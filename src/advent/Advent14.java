package advent;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Advent14 {

    private static String readFile() {
        try {
            return new String(Files.readAllBytes(new File("C:/advent/a1.txt").toPath()));
        } catch (IOException ex) {
            throw new Error(ex);
        }
    }

    private static final HashMap<String, String> map = new HashMap<>();

    private static HashMap<String, Long> pairs = new HashMap<>();

    private static final HashMap<String, Long> count = new HashMap<>();

    public static void main(String[] args) {
        final String[] lines = readFile().split("\r?\n");
        for (int i = 2; i < lines.length; i++) {
            final String line = lines[i];
            map.put(line.split(" -> ")[0], line.split(" -> ")[1]);
        }
        final String start = lines[0];
        start.chars().mapToObj(n -> String.valueOf((char) n)).forEach(n -> count.merge(n, 1L, Long::sum));
        for (int i = 0; i < start.length() - 1; i++) {
            pairs.merge(start.substring(i, i + 2), 1L, Long::sum);
        }
        for (int step = 1; step <= 40; step++) {
            var t = new HashMap<>(pairs);
            for (String key : pairs.keySet()) {
                t.merge(key.charAt(0) + map.get(key), pairs.get(key), Long::sum);
                t.merge(map.get(key) + key.charAt(1), pairs.get(key), Long::sum);
                t.merge(key, -pairs.get(key), Long::sum);
                count.merge(map.get(key), pairs.get(key), Long::sum);
            }
            pairs = t;
        }
        var l = new ArrayList<>(count.values());
        Collections.sort(l);
        System.out.println(Math.abs(l.get(0) - l.get(l.size() - 1)));
    }
}
