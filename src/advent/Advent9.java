package advent;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Advent9 {

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

    static int calcSize(Point lp) {
        if (lp.up.isEmpty()) {
            return 1;
        }
        int s = 0;
        for (Point l : lp.up) {
            s += calcSize(l);
        }
        return 1 + s;
    }

    static class Point {

        int x, y, v;
        Point down;
        List<Point> up = new ArrayList<>();

        public Point(int x, int y, int v) {
            this.x = x;
            this.y = y;
            this.v = v;
        }

        @Override
        public String toString() {
            return x + " " + y + " " + v + " " + up.toString() + ": " + calcSize(this);
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Point p) {
                return this.x == p.x && this.y == p.y;
            }
            return false;
        }
    }

    public static void main(String[] args) {
        final String[] lines = readFile().split("\r?\n");
        Point[][] board = null;
        for (int i = 0; i < lines.length; i++) {
            if (board == null) {
                board = new Point[lines.length][lines[i].length()];
            }
            for (int j = 0; j < lines[i].length(); j++) {
                board[i][j] = new Point(i, j, Integer.parseInt("" + lines[i].charAt(j)));
            }
        }
//        IntStream.rangeClosed(0, 9).boxed().flatMap(n -> IntStream.rangeClosed(0, 4).mapToObj(m -> new int[]{n, m})).sorted(Comparator.comparing(n -> n[0]));
        List<Point> lp = new ArrayList<>();
        List<Point> al = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int cl = 0;
                for (int x = -1; x < 2; x++) {
                    for (int y = -1; y < 2; y++) {
                        if (Math.abs(x) + Math.abs(y) != 1) {
                            continue;
                        }
                        try {
                            if (board[i][j].v < board[i + x][j + y].v && board[i + x][j + y].v < 9 && !al.contains(board[i + x][j + y])) {
                                board[i][j].up.add(board[i + x][j + y]);
                                al.add(board[i + x][j + y]);
                            }
                            if (board[i][j].v < board[i + x][j + y].v) {
                            } else {
                                cl++;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                        }
                    }
                }
                if (cl == 0) {
                    lp.add(board[i][j]);
                }
            }
        }
        List<Integer> ret = new ArrayList<>();
        for (Point p : lp) {
            System.out.println(p);
            ret.add(calcSize(p));
        }
        Collections.sort(ret, Comparator.reverseOrder());
        System.out.println(ret);
        System.out.println(ret.get(0) * ret.get(1) * ret.get(2));
    }
}
