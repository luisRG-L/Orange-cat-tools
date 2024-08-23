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
        lexer.nextToken(); // Skip 'print'
        if (!lexer.getToken().equals("(")) {
            SyntaxError.excepted("(", lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }
        lexer.nextToken(); // Skip '('

        String content = lexer.getToken();
        if (content.startsWith("\"") && content.endsWith("\"")) {
            // It's a string literal
            System.out.println(content.substring(1, content.length() - 1).replace("%", " ")); // Remove the quotes
        } else {
            // It's a variable
            String variableValue = lexer.getMemorySpace().variables.get(content);
            if (variableValue != null) {
                System.out.println(variableValue.replace("%", " "));
            } else {
                SyntaxError.undefined(content, lexer.getTokenIndex(), lexer.getBreakpointIndex());
            }
        }

        lexer.nextToken(); // Skip token after print content
        if (!lexer.getToken().equals(")")) {
            SyntaxError.excepted(")", lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }
        lexer.nextToken(); // Skip ')'
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
        // TODO: Generate parameter functions
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
        lexer.nextToken(); // Skip 'call'
        String functionName = lexer.getToken();
        lexer.nextToken(); // Move to the next token after functionName

        List<String> functionTokens = lexer.getMemorySpace().functions.get(functionName);

        if (functionTokens == null) {
            SyntaxError.undefined(functionName, lexer.getTokenIndex(), lexer.getBreakpointIndex());
            return;
        }

        // Create a new Lexer with the tokens of the called function
        Lexer functionLexer = new Lexer(functionTokens.toArray(new String[0]));
        functionLexer.parseCode(); // Parse the function's code
    }


    public static void parseIfConditional(Lexer lexer) {
        lexer.nextToken();
        if (!lexer.getToken().equals("(")) {
            SyntaxError.excepted("(", lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }
        lexer.nextToken();
        String operation = lexer.getToken();
        String operationValue = lexer.getMemorySpace().variables.get(operation);
        boolean booleanValue = Types.toBoolean(Types.typeof(operationValue), operationValue);
        lexer.nextToken();
        if (!lexer.getToken().equals(")")) {
            SyntaxError.excepted(")", lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }
        lexer.nextToken();
        if (!lexer.getToken().equals("{")) {
            SyntaxError.excepted("{", lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }
        if (!booleanValue) {
            // It's false
            // Don't execute the function
            while(!lexer.getToken().equals("}")) {
                lexer.nextToken();
            }
            lexer.nextToken();
            lexer.lastConditionalValue = false;
        } else {
            lexer.lastConditionalValue = true;
        }
        lexer.qualified = true;
    }

    public static void parseElseConditional(Lexer lexer) {
        lexer.nextToken();
        if(!lexer.getToken().equals("{")) {
            if (!lexer.getToken().equals("if")) {
                SyntaxError.excepted("{ or if", lexer.getTokenIndex(), lexer.getBreakpointIndex());
            }
            // else-if conditional
            parseIfConditional(lexer);
        }
        lexer.nextToken();
        if (lexer.lastConditionalValue) {
            // It's true
            // Don't execute the function
            while(!lexer.getToken().equals("}")) {
                lexer.nextToken();
            }
            lexer.nextToken();
        }
        lexer.qualified = true;
    }
}
