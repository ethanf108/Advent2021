package advent;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;

public class Advent18 {

    private static String readFile() {
        try {
            return new String(Files.readAllBytes(new File("C:/advent/a1.txt").toPath()));
        } catch (IOException ex) {
            throw new Error(ex);
        }
    }

    static String add(String s1, String s2) {
        return "[" + s1 + "," + s2 + "]";
    }

    static int num(String p) {
        if (isLeft(p)) {
            return Integer.parseInt(p.substring(p.lastIndexOf("[") + 1));
        } else if (p.endsWith("]")) {
            return Integer.parseInt(p.substring(0, p.indexOf("]")));
        } else {
            throw new IllegalStateException("OOPS");
        }
    }

    static boolean isLeft(String g) {
        return g.startsWith("[");
    }

    static String add(String orig, int n) {
        if (isLeft(orig)) {
            char[] bracks = new char[orig.lastIndexOf("[") + 1];
            Arrays.fill(bracks, '[');
            return new String(bracks) + (num(orig) + n);
        } else {
            char[] bracks = new char[orig.lastIndexOf("]") - orig.indexOf("]") + 1];
            Arrays.fill(bracks, ']');
            return (num(orig) + n) + new String(bracks);
        }
    }

    static String split(String s) {
        final String np = "[" + (num(s) / 2) + "," + ((int) Math.ceil(num(s) / 2.0)) + "]";
        if (isLeft(s)) {
            char[] bracks = new char[s.lastIndexOf("[")];
            Arrays.fill(bracks, '[');
            return "[" + new String(bracks) + np;
        } else {
            char[] bracks = new char[s.lastIndexOf("]") - s.indexOf("]")];
            Arrays.fill(bracks, ']');
            return np + new String(bracks) + "]";
        }
    }

    static int depth(String s) {
        if (isLeft(s)) {
            return s.lastIndexOf("[") + 1;
        } else {
            return (s.lastIndexOf("]") - s.indexOf("]"));
        }
    }

    static String gen(int d, char c) {
        char[] r = new char[d];
        Arrays.fill(r, c);
        return new String(r);
    }

    static boolean isExplode(String g) {
        int c = 0;
        for (char e : g.toCharArray()) {
            if (e == '[') {
                c++;
            } else if (e == ']') {
                c--;
            }
            if (c >= 5) {
                return true;
            }
        }
        return false;
    }
    static final Pattern pa = Pattern.compile("\\[[0-9]+,[0-9]+\\]");

    static String mag(String g) {
        while (g.contains("[")) {
            g = pa.matcher(g).replaceAll(n -> {
                return String.valueOf(3 * num(n.group().split(",")[0]) + 2 * num(n.group().split(",")[1]));
            });
        }
        return g;
    }

    static String reduce(String snail) {
        List<String> toks = new ArrayList<>(Arrays.asList(snail.split(",")));
        int depth = 0;
        for (int i = 0; i < toks.size(); i++) {
            final String tok = toks.get(i);
            if (isLeft(tok)) {
                depth += tok.lastIndexOf("[") + 1;
            } else {
                depth -= (tok.lastIndexOf("]") - tok.indexOf("]")) + 1;
            }
            if (isExplode(String.join("", toks)) && depth > 4 && isLeft(tok) && (i == toks.size() - 1 ? false : !isLeft(toks.get(i + 1)))) {
                final int dd = depth(tok);
                if (i > 0) {
                    toks.set(i - 1, add(toks.get(i - 1), num(tok)));
                }
                try {
                    toks.set(i + 2, add(toks.get(i + 2), num(toks.get(i + 1))));
                } catch (IndexOutOfBoundsException e) {
                }
                toks.remove(i);
                if (depth(tok) > 1) {
                    toks.set(i, gen(dd - 1, '[') + "0");
                } else {
                    toks.set(i, "0" + gen(depth(toks.get(i)), ']'));
                }
                depth = 0;
                i = -1;
            } else if (!isExplode(String.join("", toks)) && num(tok) >= 10) {
                final String g = split(tok);
                toks.set(i, g.split(",")[0]);
                toks.add(i + 1, g.split(",")[1]);
                depth = 0;
                i = -1;
            }
        }
        return String.join(",", toks);
    }

    public static void main(String[] args) {
        final String[] lines = readFile().split("\r?\n");
        List<String> ll = new ArrayList<>();
        for (String line : lines) {
            ll.add(line);
        }
        int best = 0;
        for (int a = 0; a < ll.size(); a++) {
            for (int b = 0; b < ll.size(); b++) {
                if (a == b) {
                    continue;
                }
                final int res = Integer.parseInt(mag(reduce(add(ll.get(a), ll.get(b)))));
                if (res > best) {
                    best = res;
                }
            }
        }
        System.out.println(best);
    }
}
