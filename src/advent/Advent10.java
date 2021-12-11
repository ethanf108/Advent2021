package advent;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Advent10 {

    private static final Class<?>[] NO_REMOVE_IMPORT = new Class<?>[]{Stream.class, List.class};

    private static String readFile() {
        try {
            return new String(Files.readAllBytes(new File("C:/advent/a1.txt").toPath()));
        } catch (IOException ex) {
            throw new Error(ex);
        }
    }

    public static class si implements Iterator<String> {

        private final String[] ll;
        private int i = 0;

        public si(String[] d) {
            this.ll = d;
        }

        @Override
        public boolean hasNext() {
            return i < ll.length;
        }

        @Override
        public String next() {
            return ll[i++];
        }

    }

    static int max(int... l) {
        int m = 0;
        for (int i : l) {
            if (i > m) {
                m = i;
            }
        }
        return m;
    }

    static int min(int... l) {
        int m = 99999999;
        for (int i : l) {
            if (i < m) {
                m = i;
            }
        }
        return m;
    }

    static char swit(char c) {
        return switch (c) {
            case '(' ->
                ')';
            case ')' ->
                '(';
            case '[' ->
                ']';
            case ']' ->
                '[';
            case '{' ->
                '}';
            case '}' ->
                '{';
            case '<' ->
                '>';
            case '>' ->
                '<';
            default ->
                throw new IllegalArgumentException("DDD");
        };
    }

    static long lu(char c) {
        return switch (c) {
            case ')' ->
                1;
            case ']' ->
                2;
            case '}' ->
                3;
            case '>' ->
                4;
            default ->
                throw new IllegalArgumentException("E");
        };
    }

    public static void main(String[] args) {
        final String[] lines = readFile().split("\r?\n");
        List<Long> ll = new ArrayList<>();
        FF:
        for (final String line : lines) {
            Stack<Character> chars = new Stack<>();

            for (char c : line.toCharArray()) {
                if (c == '(' || c == '[' || c == '{' || c == '<') {
                    chars.push(c);
                } else if (!chars.pop().equals(swit(c))) {
                    continue FF;
                }
            }
            long t = 0;
            while (!chars.isEmpty()) {
                t *= 5;
                t += lu(swit(chars.pop()));
            }
            ll.add(t);
        }
        Collections.sort(ll);
        System.out.println(ll);
        System.out.println(ll.get(ll.size() / 2));
    }
}
