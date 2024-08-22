package com.orangecat.ocat.parsing;

import com.orangecat.ocat.Lexer;
import com.orangecat.ocat.typing.*;
import com.orangecat.ocat.errors.SyntaxError;
import com.orangecat.ocat.typing.*;

public class ParseFunctions {
    public static void parseVariableDeclaration(Lexer lexer) {
        VariableType variableType = Types.getType(
                lexer.getToken()
        );
        lexer.nextToken();
        String variableName = lexer.getToken();
        /*if (!(TypeVerifying.isVarName(variableName))) {
            SyntaxError.badName(variableName, "variable", lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }*/
        lexer.nextToken();
        if (!lexer.getToken().equals("=")) {
            SyntaxError.excepted("=", lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }
        lexer.nextToken();
        String value = lexer.getToken();
        if(!TypeVerifying.isType(variableType, value)) {
            SyntaxError.noType(value, lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }
        lexer.getMemorySpace().addVariable(variableName, value);
    }

    public static void parsePrintStatement(Lexer lexer) {
        lexer.nextToken();
        if (!lexer.getToken().equals("(")) {
            SyntaxError.excepted("(", lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }
        lexer.nextToken();
        if (!(TypeVerifying.isType(VariableType.STRING, lexer.getToken()))) {
            // Is a variable
            System.out.println(lexer.getMemorySpace().variables.get(lexer.getToken()));
        }else {
            System.out.println(lexer.getToken()
                    .replace("\"", "")
                    .replace("%", " ")
            );
        }
        lexer.nextToken();
        if (!lexer.getToken().equals(")")) {
            SyntaxError.excepted(")", lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }
        lexer.nextToken();
    }
}
