package advent;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Advent15 {

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

    static Integer[][] values;

    static Point[][] board;

    static class Point {

        int x, y, v;

        public Point(int x, int y, int v) {
            this.x = x;
            this.y = y;
            this.v = v;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 43 * hash + this.x;
            hash = 43 * hash + this.y;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Point p) {
                return p.x == x && p.y == y;
            }
            return false;
        }

        @Override
        public String toString() {
            return "" + v;
        }
    }

    static Point[] next(Point p) {
        ArrayList<Point> l = new ArrayList<>(4);
        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
                if (Math.abs(dx) + Math.abs(dy) != 1 || p.x + dx < 0 || p.y + dy < 0 || p.x + dx >= board.length || p.y + dy >= board[0].length) {
                    continue;
                }
                if (board[p.x + dx][p.y + dy] != null) {
                    l.add(board[p.x + dx][p.y + dy]);
                }
            }
        }
        if (l.size() == 0) {
            return new Point[0];
        }
        Point[] ret = new Point[l.size()];
        l.toArray(ret);
        return ret;
    }

    static void pf(Point last) {
        boolean changed = false;
        do {
            changed = false;
            for (int x = 0; x < board.length; x++) {
                for (int y = 0; y < board.length; y++) {
                    Point p = board[x][y];
                    for (Point o : next(p == null ? new Point(x, y, 0) : p)) {
                        if (o == null) {
                            continue;
                        }
                        if (p == null) {
                            p = board[x][y] = new Point(x, y, o.v + values[x][y]);
                            changed = true;
                        } else if (o.v + values[x][y] < p.v) {
                            p.v = o.v + values[x][y];
                            changed = true;
                        }
                    }
                }
            }
        } while (changed);
    }

    public static void main(String[] args) {
        final String[] lines = readFile().split("\r?\n");
        List<List<Integer>> ll = new ArrayList<>();
        for (String line : lines) {
            List<Integer> o = new ArrayList<>();
            for (char c : line.toCharArray()) {
                o.add(Integer.parseInt(c + ""));
            }
            var ff = new ArrayList<>(o);
            for (int d = 1; d <= 4; d++) {
                for (int i : ff) {
                    int g = i + d;
                    o.add(i + d > 9 ? i + d - 9 : i + d);
                }
            }
            ll.add(o);
        }
        var fll = new ArrayList<>(ll);
        for (int i = 1; i <= 4; i++) {
            final int ii = i;
            for (List<Integer> li : fll) {
                ll.add(li.stream().map(n -> n + ii > 9 ? n + ii - 9 : n + ii).collect(Collectors.toList()));
            }
        }
        final List<Integer[]> lia = ll.stream().map(n -> {
            Integer[] ret = new Integer[n.size()];
            ret = n.toArray(ret);
            return ret;
        }).collect(Collectors.toList());
        values = new Integer[lia.size()][lia.get(0).length];
        values = lia.toArray(values);
        board = new Point[values.length][values[0].length];
        final Point end = new Point(board.length - 1, board[0].length - 1, values[values.length - 1][values[0].length - 1]);
        board[board.length - 1][board[0].length - 1] = end;
        pf(end);
        System.out.println(board[0][0]);

    }
}
