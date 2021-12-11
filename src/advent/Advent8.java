package advent;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Advent8 {

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

    final static String ABC = "abcdefg";

    static List<List<String>> getOfLength(String[] s, int l) {
        List<List<String>> ret = new ArrayList<>();
        for (String g : s) {
            if (g.length() == l) {
                ret.add(sc(g));
            }
        }
        return ret;
    }

    static List<String> sc(String s) {
        return new ArrayList<>(Arrays.asList(s.split("")));
    }

    /*
    2: 1
    3: 7
    4: 4
    5: 2, 3, 5
    6: 0, 6, 9
    7: 8

    a: 0, 2, 3, 5, 6, 7, 8
    b: 0, 4, 5, 6, 8
    c: 0, 1, 2, 3, 4, 7, 8
    d: 2, 3, 4, 5, 6, 8
    e: 0, 2, 6, 8
    f: 0, 1, 3, 4, 5, 6, 7, 8
    g: 0, 2, 3, 5, 6, 8
     */
    static int solve(String line) {
        String[] before = line.split("\\|")[0].split(" +");
        String[] after = line.split("\\|")[1].strip().split(" +");
        List<String>[] answer = new List[7];
        for (int i = 0; i < 7; i++) {
            answer[i] = sc(ABC);
        }

        var s7 = getOfLength(before, 3).get(0);
        s7.removeAll(getOfLength(before, 2).get(0));
        answer[0] = List.of(s7.get(0));

        var s6 = sc(ABC);
        s6.removeAll(getOfLength(before, 6).get(0));
        var s9 = sc(ABC);
        s9.removeAll(getOfLength(before, 6).get(1));
        var s0 = sc(ABC);
        s0.removeAll(getOfLength(before, 6).get(2));
        answer[2] = new ArrayList<>(List.of(s6.get(0), s9.get(0), s0.get(0)));
        answer[3] = new ArrayList<>(List.of(s6.get(0), s9.get(0), s0.get(0)));
        answer[4] = new ArrayList<>(List.of(s6.get(0), s9.get(0), s0.get(0)));

        answer[4].removeAll(getOfLength(before, 4).get(0));
        answer[2].removeAll(answer[4]);
        answer[3].removeAll(answer[4]);

        answer[2].retainAll(getOfLength(before, 2).get(0));
        answer[3].removeAll(getOfLength(before, 2).get(0));

        answer[5] = new ArrayList<>(getOfLength(before, 2).get(0));
        answer[5].removeAll(answer[2]);
        //DONE 0 2 3 4 5

        answer[6].removeAll(getOfLength(before, 4).get(0));
        answer[6].removeAll(List.of(answer[0].get(0), answer[4].get(0)));

        answer[1].removeAll(List.of(answer[0].get(0), answer[6].get(0), answer[2].get(0), answer[3].get(0), answer[4].get(0), answer[5].get(0)));

//        System.out.println(" " + answer[0].get(0) + " \n" + answer[1].get(0) + " " + answer[2].get(0) + "\n " + answer[3].get(0) + " \n" + answer[4].get(0) + " " + answer[5].get(0) + "\n " + answer[6].get(0));
        int t = 0;
        for (String n : after) {
            t *= 10;
            switch (n.length()) {
                case 2:
                    t += 1;
                    continue;
                case 3:
                    t += 7;
                    continue;
                case 4:
                    t += 4;
                    continue;
                case 7:
                    t += 8;
                    continue;
            }
            final String[] out = new String[]{answer[0].get(0), answer[1].get(0), answer[2].get(0), answer[3].get(0), answer[4].get(0), answer[5].get(0), answer[6].get(0),};
            t += figure(out, n);
        }
        System.out.println(t);
        return t;
    }

    static int aio(String[] l, String s) {
        for (int i = 0; i < l.length; i++) {
            if (l[i] != null && l[i].equals(s)) {
                return i;
            }
        }
        throw new IllegalStateException("D");
    }
//235

    static int figure(String[] templ, String num) {
        for (char c : num.toCharArray()) {
            templ[aio(templ, "" + c)] = null;
        }
        if (templ[0] == null && templ[1] == null && templ[2] == null && templ[4] == null && templ[5] == null && templ[6] == null) {
            return 0;
        }
        if (templ[0] == null && templ[1] == null && templ[3] == null && templ[4] == null && templ[5] == null && templ[6] == null) {
            return 6;
        }
        if (templ[0] == null && templ[1] == null && templ[2] == null && templ[3] == null && templ[5] == null && templ[6] == null) {
            return 9;
        }
        if (templ[0] == null && templ[2] == null && templ[3] == null && templ[5] == null && templ[6] == null) {
            return 3;
        }
        if (templ[0] == null && templ[2] == null && templ[3] == null && templ[4] == null && templ[6] == null) {
            return 2;
        }
        if (templ[0] == null && templ[1] == null && templ[3] == null && templ[5] == null && templ[6] == null) {
            return 5;
        }
        throw new IllegalArgumentException("A");
    }

    public static void main(String[] args) {
        final String[] lines = readFile().split("\r?\n");
        System.out.println(Arrays.stream(lines).mapToInt(Advent8::solve).sum());
    }
}
