package com.orangecat.ocat.typing;


public class Types {
    public static final String[] VARTYPES = {
            "int",
            "decimal",
            "string",
            "bool"
    };

    public static VariableType getType(String type) {
        return switch (type) {
            case "int" -> VariableType.INTEGER;
            case "decimal" -> VariableType.DECIMAL;
            case "string" -> VariableType.STRING;
            case "bool" -> VariableType.BOOLEAN;
            default -> VariableType.UNKNOWN;
        };
    }

    public static boolean toBoolean(VariableType variableType, String operationValue) {
        return switch (variableType) {
            case UNKNOWN -> false;
            case DECIMAL -> Integer.parseInt(operationValue) > 0;
            case STRING -> !operationValue.isEmpty();
            case BOOLEAN -> operationValue.equals("true");
            case INTEGER -> Double.parseDouble(operationValue) > 0;
        };
    }

    public static VariableType typeof(String operationValue) {
        if (TypeVerifying.isType(VariableType.BOOLEAN, operationValue)) {
            return VariableType.BOOLEAN;
        } else if (TypeVerifying.isType(VariableType.INTEGER, operationValue)) {
            return VariableType.INTEGER;
        } else if (TypeVerifying.isType(VariableType.DECIMAL, operationValue)) {
            return VariableType.DECIMAL;
        } else if (TypeVerifying.isType(VariableType.STRING, operationValue)) {
            return VariableType.STRING;
        } else {
            return VariableType.UNKNOWN;
        }
    }
}
