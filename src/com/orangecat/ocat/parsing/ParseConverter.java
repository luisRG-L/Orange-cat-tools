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
}
