package advent;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Advent25 {

    private static String readFile() {
        try {
            return new String(Files.readAllBytes(new File("C:/advent/a1.txt").toPath()));
        } catch (IOException ex) {
            throw new Error(ex);
        }
    }

    private static record Point(int x, int y) {

    }

    static boolean run(char[][] board) {
        boolean changed = false;
        List<Point> move = new ArrayList<>();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                if (board[x][y] != '>') {
                    continue;
                }
                if (board[x][(y + 1) % board[0].length] == '.') {
                    move.add(new Point(x, y));
                }
            }
        }
        for (Point p : move) {
            board[p.x][(p.y + 1) % board[0].length] = '>';
            board[p.x][p.y] = '.';
            changed = true;
        }
        move.clear();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                if (board[x][y] != 'v') {
                    continue;
                }
                if (board[(x + 1) % board.length][y] == '.') {
                    move.add(new Point(x, y));
                }
            }
        }
        for (Point p : move) {
            board[(p.x + 1) % board.length][p.y] = 'v';
            board[p.x][p.y] = '.';
            changed = true;
        }
        return changed;
    }

    public static void main(String[] args) {
        final String[] lines = readFile().split("\r?\n");
        char[][] board = new char[lines.length][lines[0].length()];
        for (int i = 0; i < board.length; i++) {
            final String line = lines[i];
            for (int j = 0; j < line.length(); j++) {
                board[i][j] = switch (line.charAt(j)) {
                    case '>','v','.' ->
                        line.charAt(j);
                    default ->
                        throw new IllegalArgumentException("BRUH");
                };
            }
        }
        int step = 1;
        for (; run(board); step++) {
        }
        System.out.println(step);
    }
}
