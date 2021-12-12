package advent;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Advent12 {

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

    static class Node {

        String name;
        Set<Node> c = new HashSet<>();

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Node n) {
                return this.name.equals(n.name);
            }
            return false;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    static boolean isLegal(List<Node> l) {
//        l.removeIf(n -> n.name.equals("start") || n.name.equals("end") || Character.isUpperCase(n.name.charAt(0)));
        boolean has = false;
        Set<Node> s = new HashSet<>();
        for (Node n : l) {
            if (!Character.isLowerCase(n.name.charAt(0))) {
                continue;
            }
            if (s.contains(n)) {
                if (has || n.name.equals("start") || n.name.equals("end")) {
                    return false;
                }
                has = true;
            }
            s.add(n);
        }
        return true;
    }

    static int as(Node start, Node end, List<Node> path) {
        if (start.equals(end)) {
            // System.out.println(path);
            return 1;
        }
        int sum = 0;
        for (Node n : start.c) {
            ArrayList<Node> l = new ArrayList<>(path);
            l.add(n);
            if (isLegal(l)) {
                sum += as(n, end, l);
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        final String[] lines = readFile().split("\r?\n");
        HashMap<String, Node> nodes = new HashMap<>();
        for (String line : lines) {
            String l = line.split("-")[0];
            String r = line.split("-")[1];
            Node a = nodes.getOrDefault(l, null);
            if (a == null) {
                a = new Node();
                a.name = l;
            }
            Node b = nodes.getOrDefault(r, null);
            if (b == null) {
                b = new Node();
                b.name = r;
            }
            b.c.add(a);
            a.c.add(b);
            nodes.put(l, a);
            nodes.put(r, b);
        }
        final Node start = nodes.get("start");
        final Node end = nodes.get("end");
        System.out.println(as(start, end, new ArrayList<>(List.of(start))));
    }
}
