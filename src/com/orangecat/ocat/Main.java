package com.orangecat.ocat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Insert the input file");
        }

        String inputFilePath = args[0];
        String extension = inputFilePath.substring(inputFilePath.lastIndexOf('.')).replace(".", "");
        if (!(extension.equals("ocat") || extension.equals("oc"))){
            System.err.println("The input file extension must be '.ocat' or '.oc'");
        }
        try {
            String content = new String(Files.readAllBytes(Paths.get(inputFilePath)));
            String[] spacedContent = content.split("\\s+");

            new Lexer(spacedContent);
        } catch (IOException e) {
			System.err.println("Error al leer el archivo: " + inputFilePath);
        }
    }
}
