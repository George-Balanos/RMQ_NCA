// Various input methods
// Source :  R. Sedgewick, Algorithms in Java, parts 1-4

import java.io.*;

public class In {

    private static int c;

    private static boolean blank() {
        return Character.isWhitespace((char) c);
    }

    private static void readC() {
        try {
            c = System.in.read();
        } catch (IOException e) {
            c = -1;
        }
    }

    // initialize input stream
    public static void init() {
        readC();
    }

     // check if input stream is empty
    public static boolean empty() {
        return c == -1;
    }

    // read string from input stream
    public static String getString() {
        if (empty()) {
            return null;
        }
        String s = "";
        while (!(empty() || blank())) {
            s += (char) c;
            readC();
        }
        while (!empty() && blank()) {
            readC();
        }
        return s;
    }

    // read integer from input stream
    public static int getInt() {
        return Integer.parseInt(getString());
    }

    // read long integer from input stream
    public static long getLong() {
        return Long.parseLong(getString());
    }

    // read double from input stream
    public static double getDouble() {
        return Double.parseDouble(getString());
    }
}
