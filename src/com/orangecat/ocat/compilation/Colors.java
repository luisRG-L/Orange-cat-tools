package com.orangecat.ocat.compilation;

import java.io.OutputStream;
import java.io.PrintStream;

public class Colors extends PrintStream {

    // Códigos ANSI para colores
    public static final String RESET = "\033[0m";  // Reset color
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    // Códigos ANSI para colores de fondo
    public static final String BLACK_BG = "\033[40m";
    public static final String RED_BG = "\033[41m";
    public static final String GREEN_BG = "\033[42m";
    public static final String YELLOW_BG = "\033[43m";
    public static final String BLUE_BG = "\033[44m";
    public static final String PURPLE_BG = "\033[45m";
    public static final String CYAN_BG = "\033[46m";
    public static final String WHITE_BG = "\033[47m";

    public Colors(OutputStream out) {
        super(out);
    }

    public void println(String msg, String textColor, String bgColor) {
        super.println(textColor + bgColor + msg + RESET);
    }

    public void println(String msg, String textColor) {
        super.println(textColor + msg + RESET);
    }

    public void print(String msg, String textColor, String bgColor) {
        super.print(textColor + bgColor + msg + RESET);
    }

    public void print(String msg, String textColor) {
        super.print(textColor + msg + RESET);
    }
}
