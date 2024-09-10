package com.orangecat.ocat.parsing;

import com.orangecat.ocat.Lexer;
import com.orangecat.ocat.http.HTTPUtilities;
import com.orangecat.ocat.typing.*;
import com.orangecat.ocat.errors.SyntaxError;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.orangecat.ocat.compilation.Colors;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.swing.*;

public class ParseFunctions {

    public static void parsePrintStatement(Lexer lexer) {
        lexer.nextToken(); // Skip 'print'
        if (!lexer.getToken().equals("(")) {
            SyntaxError.excepted("(", lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }
        lexer.nextToken(); // Skip '('

        String content = lexer.getToken();
        if (content.startsWith("\"") && content.endsWith("\"")) {
            // It's a string literal
            System.out.println(content.replace("\"", "").replace("%", " ")); // Remove the quotes
        } else {
            // It's a variable
            String variableValue = lexer.getMemorySpace().variables.get(content);
            if (variableValue != null) {
                System.out.println(variableValue.replace("%", " ").replace("\"", ""));
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

    public static void parseWarnStatement(Lexer lexer) {
        lexer.nextToken(); // Skip 'warn'
        if (!lexer.getToken().equals("(")) {
            SyntaxError.excepted("(", lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }
        lexer.nextToken(); // Skip '('

        String content = lexer.getToken();
        if (content.startsWith("\"") && content.endsWith("\"")) {
            // It's a string literal
            new Colors(System.out).println("Warn:"+content.replace("\"", "").replace("%", " "), Colors.YELLOW_BG, Colors.WHITE); // Remove the quotes
        } else {
            // It's a variable
            String variableValue = lexer.getMemorySpace().variables.get(content);
            if (variableValue != null) {
                new Colors(System.out).println(variableValue.replace("%", " ").replace("\"", ""), Colors.YELLOW_BG, Colors.WHITE);
            } else {
                SyntaxError.undefined(content, lexer.getTokenIndex(), lexer.getBreakpointIndex());
            }
        }

        lexer.nextToken(); // Skip token after print content
        if (!lexer.getToken().equals(")")) {
            SyntaxError.excepted(")", lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }
        lexer.nextToken();
    }

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
        if (operationValue == null) {
            SyntaxError.undefined(operation, lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }
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

    public static void parseRepeat(Lexer lexer) {
        lexer.nextToken();
        if (!lexer.getToken().equals("(")) {
            SyntaxError.excepted("(", lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }
        lexer.nextToken();
        int count = 0;
        if (lexer.getToken().matches("\\d+")) {
            count = Integer.parseInt(lexer.getToken());
        } else {
            // It's a variable
            String varName = lexer.getToken();
            String varValue = lexer.getMemorySpace().variables.get(varName);
            if (varValue == null) {
                SyntaxError.undefined(varName, lexer.getTokenIndex(), lexer.getBreakpointIndex());
            }
            assert varValue != null;
            count = Integer.parseInt(varValue);
        }
        lexer.nextToken();
        if (!lexer.getToken().equals(")")) {
            SyntaxError.excepted(")", lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }
        lexer.nextToken();
        if (!lexer.getToken().equals("{")) {
            SyntaxError.excepted("{", lexer.getTokenIndex(), lexer.getBreakpointIndex());
        }
        lexer.nextToken();
        int tknpos = lexer.getTokenPos();

    }

    public static void parseImport(Lexer lexer) {
        /*
        lexer.nextToken();
        String libname = lexer.getToken();
        String inputFilePath = libname + ".ocat";
        try {
            String content = new String(Files.readAllBytes(Paths.get(inputFilePath)));
            String[] spacedContent = content.split("\\s+");

            Lexer lexer1 = new Lexer(spacedContent);
            lexer1.getMemorySpace().variables.forEach(lexer.getMemorySpace()::addVariable);
        } catch (IOException e) {
            System.err.println("HOLa");
        }*/
    }

    public static void parseTest(Lexer lexer) {
        lexer.nextToken();
        String varName = lexer.getToken();
        String varValue = lexer.getMemorySpace().variables.get(varName);
        System.out.println("Variable name: "+varName);
        System.out.println("Variable value: "+varValue);
        System.out.println("Variable type: "+Types.typeof(varValue));
        lexer.nextToken();
    }

    public static void parseFront(Lexer lexer) {
        lexer.nextToken();
        String command = lexer.getToken();

        switch(command) {
            case "build" -> {
                // Main configurations
                lexer.html.append("<title>Orange Cat App</title>");
                lexer.html.append("<style>");
                lexer.html.append("</style>");
                System.out.println("Enabling...");
                // Client URL Web server
                try {
                    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

                    // Create main context for the html page
                    server.createContext("/", new HttpHandler() {
                        @Override
                        public void handle(HttpExchange exchange) throws IOException {
                            String htmlContent = lexer.html.toString(); // Get the dynamic html
                            exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                            exchange.sendResponseHeaders(200, htmlContent.getBytes().length);

                            // Send the html content as response
                            OutputStream os = exchange.getResponseBody();
                            os.write(htmlContent.getBytes());
                            os.close();
                        }
                    });

                    // Init the server
                    server.setExecutor(null); // Use the server executor
                    server.start();
                    System.out.println("Server running at http://localhost:8080");

                    // Wait for the server closing
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Press any key to stop the server...");
                    sc.next();

                    // Stop the server
                    server.stop(0);
                    System.out.println("Server stopped");

                } catch (IOException e) {
                    // TODO: END
                }
            }
            case "add" -> {
                lexer.nextToken();
                String tag = lexer.getToken();
                if(!tag.startsWith("`")) {
                    SyntaxError.noType("The front add pattern must be an a superstring", lexer.getTokenIndex(), lexer.getBreakpointIndex());
                }
                lexer.html.append(HTTPUtilities.parseHTTPTag(tag.replace("`", "")));
            }
            case "" -> {

            }
        }
    }
}
