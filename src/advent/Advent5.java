package advent;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Advent5 {

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

    public static void main(String[] args) throws IOException {
        final String[] lines = readFile().split("\r?\n");
        int mx = Arrays.stream(lines).flatMap(n -> Arrays.stream(n.split(" -> "))).map(n -> n.split(",")[0]).mapToInt(Integer::parseInt).max().getAsInt();
        int my = Arrays.stream(lines).flatMap(n -> Arrays.stream(n.split(" -> "))).map(n -> n.split(",")[1]).mapToInt(Integer::parseInt).max().getAsInt();
        int[][] board = new int[mx + 1][my + 1];
        for (String line : lines) {
            int x1 = Integer.parseInt(line.split(" -> ")[0].split(",")[0]);
            int y1 = Integer.parseInt(line.split(" -> ")[0].split(",")[1]);
            int x2 = Integer.parseInt(line.split(" -> ")[1].split(",")[0]);
            int y2 = Integer.parseInt(line.split(" -> ")[1].split(",")[1]);
            if (x1 == x2) {
                for (int i = Math.min(y1, y2); i <= max(y1, y2); i++) {
                    board[x1][i]++;
                }
            } else if (y1 == y2) {
                for (int i = Math.min(x1, x2); i <= max(x1, x2); i++) {
                    board[i][y1]++;
                }
            } else {
                for (int x = 0; x <= mx; x++) {
                    for (int y = 0; y < my; y++) {
                        if (Math.abs(x - x1) == Math.abs(y - y1) && x >= min(x1, x2) && x <= max(x1, x2) && y >= min(y1, y2) && y <= max(y1, y2)) {
                            board[x][y]++;
                        }
                    }
                }
            }
        }
        System.out.println(Arrays.stream(board).parallel().flatMapToInt(IntStream::of).filter(n -> n >= 2).count());
    }
}
