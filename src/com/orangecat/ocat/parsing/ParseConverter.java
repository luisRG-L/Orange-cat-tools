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

    public static boolean isSwitch(String token) {
        return (
                token.equals("switch")
        );
    }

    public static boolean isImport(String token) {
        return (
                token.equals("import")
                );
    }

    public static boolean isTest(String token) {
        return (
                token.equals("test")
                );
    }

    public static boolean isWarn(String token) {
        return (
                token.equals("warn")
                );
    }

    public static boolean isError(String token) {
        return (
                token.equals("err")
                );
    }

    public static boolean isFront(String token) {
        return (
                token.equals("front")
        );
    }

    public static boolean isRepeat(String token) {
        return (
                token.equals("repeat")
                );
    }

    public static boolean isSave(String token) {
        return (
                token.equals("save")
        );
    }

    public static boolean isBack(String token) {
        return (
                token.equals("back")
        );
    }
}
