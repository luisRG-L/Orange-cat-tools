package com.orangecat.ocat.errors;

public class SyntaxError {
    public static void excepted(String err, int token, int breakPoint) {
        err("Excepted token "+err, token, breakPoint);
    }

    public static void badName(String name, String object, int tokenIndex, int breakpointIndex) {
        err("Bad named "+object+": "+name, tokenIndex, breakpointIndex);
    }

    public static void noType(String value, int tokenIndex, int breakpointIndex) {
        err("The statement value: "+value+"don't match the excepted value pattern", tokenIndex, breakpointIndex);
    }

    public static void err(String ref, int token, int breakPoint) {
        System.err.println("Syntax error on token " + token);
        System.err.println("\t"+ref);
        System.err.println("Breakpoint " + breakPoint);
        System.exit(1);
    }


    public static void undefined(String token, int tokenIndex, int breakpointIndex) {
        err("Undefined token "+token, tokenIndex, breakpointIndex);
    }
}
