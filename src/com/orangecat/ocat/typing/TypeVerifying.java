package com.orangecat.ocat.typing;

import com.orangecat.ocat.parsing.ParseUtilities;

public class TypeVerifying {
    private static final String[] BADNAMED_CHARS = {
            "[", "]", "!", "¡", "(", ")",
            "{", "}", "<", ">",
            "$", "%", "^", "&", "*", "+",
            "=", "|", "\\", "/", ":", ";",
            "\"", "'", ",", ".", "?", "~",
            "`", " ", "\t", "\n", "\r", "°",
            "¬", "¿", "§", "¶", "•", "÷",
            "×", "±", "¶", "©", "®", "™",
            "†", "‡", "‰", "‹", "›", "€",
            "¥", "£", "¢", "µ", "¦", "§"
    };


    public static boolean isVarName(String var) {
        return (
                ParseUtilities.containsInList(BADNAMED_CHARS, var)
        );
    }

    public static boolean isType(VariableType variableType, String value) {
        return switch (variableType) {
            case UNKNOWN -> true;
            case BOOLEAN -> value.equals("true") || value.equals("false");
            case DECIMAL -> value.matches("^-?\\d*\\.\\d+$");
            case STRING -> value.startsWith("\"") && value.endsWith("\"");
            case INTEGER -> value.matches("^[+-]?\\d+$");
        };
    }
}