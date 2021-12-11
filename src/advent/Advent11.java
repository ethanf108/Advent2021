package advent;

import java.awt.Point;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Advent11 {

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

    static <T, R> R[] arrayMapDeep(T[] arr, Function<? super T, ? extends R> mapper) {
        List<R> acc = new ArrayList<>();
        Class<?> clazz = null;
        for (T obj : arr) {
            final R val = mapper.apply(obj);
            acc.add(val);
            clazz = val.getClass();
        }
        @SuppressWarnings("unchecked")
        R[] ret = (R[]) Array.newInstance(clazz, acc.size());
        for (int i = 0; i < acc.size(); i++) {
            ret[i] = acc.get(i);
        }
        return ret;
    }

    public static void main(String[] args) {
        Object[] l = new Object[1];
        for (int i = 0; i < 1000; i++) {
            l[0] = new Object[1];
            l = (Object[]) l[0];
        }
    }

    public static void main_(String[] args) {
        final String[] lines = readFile().split("\r?\n");
        int[][] board = new int[10][10];
        for (int i = 0; i < 10; i++) {
            final String line = lines[i];
            for (int j = 0; j < 10; j++) {
                board[i][j] = Integer.parseInt(line.charAt(j) + "");
            }
        }
        int tf = 0;
        for (int step = 1; step <= 10000; step++) {
            tf = 0;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    board[i][j]++;
                }
            }
            List<Point> al = new ArrayList<>();
            FF:
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (board[i][j] > 9 && !al.contains(new Point(i, j))) {
                        al.add(new Point(i, j));
                        for (int x = -1; x < 2; x++) {
                            for (int y = -1; y < 2; y++) {
                                if (x == 0 && y == 0) {
                                    continue;
                                }
                                try {
                                    board[i + x][y + j]++;
                                } catch (ArrayIndexOutOfBoundsException e) {
                                }
                            }
                        }
                        i = -1;
                        tf++;
                        continue FF;
                    }
                }
            }

//            al.forEach(n -> board[n.x][n.y] = 0);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (board[i][j] > 9) {
                        board[i][j] = 0;
                    }
                }
            }
            Collections.sort(al, Comparator.comparing(n -> n.x));
            //al.forEach(n -> System.out.println("] " + n.x + " " + n.y));
//            System.out.println(Arrays.deepToString(board).replaceAll("\\], \\[", "]\n["));
//            System.out.println(step + " " + tf);
            if (tf == 100) {

                System.out.println(step);
                return;
            }
        }
        System.out.println(tf);
    }
}
