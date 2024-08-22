package com.orangecat.ocat.typing;

import com.orangecat.ocat.parsing.ParseUtilities;

public class Types {
    public static final String[] VARTYPES = {
            "int",
            "decimal",
            "string"
    };

    public static VariableType getType(String type) {
        return switch (type) {
            case "int" -> VariableType.INTEGER;
            case "decimal" -> VariableType.DECIMAL;
            case "string" -> VariableType.STRING;
            default -> VariableType.UNKNOWN;
        };
    }
}
