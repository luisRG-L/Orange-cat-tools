package com.orangecat.ocat.parsing;

import com.orangecat.ocat.Lexer;
import com.orangecat.ocat.typing.*;
import com.orangecat.ocat.errors.SyntaxError;

import java.util.ArrayList;
import java.util.List;

public class ParseFunctions {
    public static void parseVariableDeclaration(Lexer lexer) {
        VariableType variableType = Types.getType(
                lexer.getToken()
        );
        lexer.nextToken();
        String variableName = lexer.getToken();
        /*
        if (!(TypeVerifying.isVarName(variableName))) {
            SyntaxError.badName(variableName, "variable", lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }
        */
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

    public static void parseFunctionDeclaration(Lexer lexer) {
        lexer.nextToken();
        String functionName = lexer.getToken();
        /*
        if (!TypeVerifying.isVarName(lexer.getToken())) {

        }
        */
        lexer.nextToken();
        if (!lexer.getToken().equals("(")) {
            SyntaxError.excepted("(", lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }
        lexer.nextToken();
        // TODO: Generate paramenter functions
        if (!lexer.getToken().equals(")")) {
            SyntaxError.excepted(")", lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }
        lexer.nextToken();
        if(!lexer.getToken().equals("{")) {
            SyntaxError.excepted("{", lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }
        lexer.nextToken();
        List<String> tokens = new ArrayList<>();
        while(!lexer.getToken().equals("}")) {
            tokens.add(lexer.getToken());
            lexer.nextToken();
        }

        lexer.getMemorySpace().addFunction(functionName, tokens);
    }

    public static void parseFunctionCall(Lexer lexer) {
        lexer.nextToken();
        String functionName = lexer.getToken();
        lexer.nextToken();

        List<String> functionTokens = lexer.getMemorySpace().functions.get(functionName);

        if (functionTokens == null) {
            SyntaxError.undefined(functionName, lexer.getTokenIndex(), lexer.getBreakpointIndex());
            return;
        }

        String[] keywords = functionTokens.toArray(new String[0]);

        new Lexer(keywords).parseCode();
    }

}
