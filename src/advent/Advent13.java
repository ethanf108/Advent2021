package advent;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Advent13 {

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

    static void foldX(boolean[][] board, int xf) {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                if (x <= xf || !board[x][y]) {
                    continue;
                }
                board[2 * xf - x][y] |= board[x][y];
                board[x][y] = false;
            }
        }
    }

    static void foldY(boolean[][] board, int yf) {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                if (y <= yf || !board[x][y]) {
                    continue;
                }
                board[x][2 * yf - y] |= board[x][y];
                board[x][y] = false;
            }
        }
    }

    public static void main(String[] args) {
        final String[] lines = readFile().split("\r?\n");
        int mx = 0;
        int my = 0;
        for (String line : lines) {
            if (line.matches("\\d+,\\d+")) {
                int x = Integer.parseInt(line.split(",")[0]);
                if (x > mx) {
                    mx = x;
                }
                int y = Integer.parseInt(line.split(",")[1]);
                if (y > my) {
                    my = y;
                }
            }
        }
        boolean board[][] = new boolean[mx + 1][my + 1];
        for (String line : lines) {
            if (line.isBlank()) {
                break;
            }
            int x = Integer.parseInt(line.split(",")[0]);
            int y = Integer.parseInt(line.split(",")[1]);
            board[x][y] = true;
        }
        for (String line : lines) {
            if (!line.startsWith("fold along")) {
                continue;
            }
            final boolean isFoldX = line.substring(11, 12).equals("x");
            final int num = Integer.parseInt(line.substring(13));
            if (isFoldX) {
                foldX(board, num);
            } else {
                foldY(board, num);
            }
        }
        int s = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] ? "#" : ".");
                if (board[i][j]) {
                    s++;
                }
            }
            System.out.println();
        }
        System.out.println(s);
    }
}
