package advent;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Advent7 {

    private static final Class<?>[] NO_REMOVE_IMPORT = new Class<?>[]{Stream.class, List.class};

    private static String readFile() {
        try {
            System.out.println("READ");
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

    public static void main(String[] args) {
        System.out.println(IntStream
                .rangeClosed(Arrays
                        .stream(readFile()
                                .split("\r?\n")[0]
                                .split(","))
                        .parallel()
                        .mapToInt(Integer::parseInt)
                        .min()
                        .getAsInt(),
                        Arrays
                                .stream(readFile()
                                        .split("\r?\n")[0]
                                        .split(","))
                                .parallel()
                                .mapToInt(Integer::parseInt)
                                .max()
                                .getAsInt())
                .parallel()
                .map(mid -> Arrays
                .stream(readFile()
                        .split("\r?\n")[0]
                        .split(","))
                .parallel()
                .mapToInt(Integer::parseInt)
                .map(n -> (Math.abs(mid - n) * (Math.abs(mid - n) + 1)) / 2)
                .sum())
                .min()
                .getAsInt());
    }
}
