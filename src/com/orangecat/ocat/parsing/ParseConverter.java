package com.orangecat.ocat.parsing;

import com.orangecat.ocat.typing.*;

public class ParseConverter {
    public static boolean isVartype(String token) {
        return (
                ParseUtilities.inList(Types.VARTYPES, token)
                );
    }

    public static boolean isPrint(String token) {
        return (
                token.equals("print")
                );
    }

    public static boolean isBreakpoint(String token) {
        return (
                token.equals(";")
                );
    }

    public static boolean isFunctionDeclaration(String token) {
        return (
                token.equals("func")
                );
    }

    public static boolean isFunctionCall(String token) {
        return (
                token.equals("call")
                );
    }

    public static boolean isIf(String token) {
        return (
                token.equals("if")
                );
    }

    public static boolean isElse(String token) {
        return (
                token.equals("else")
                );
    }
}
