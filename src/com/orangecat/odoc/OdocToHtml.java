package com.orangecat.odoc;

import java.io.*;
import java.nio.file.*;
import java.util.regex.*;

public class OdocToHtml {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Uso: java OdocToHtml <input_file>");
            System.exit(1);
        }

        String inputFilePath = args[0];
        try {
            String content = new String(Files.readAllBytes(Paths.get(inputFilePath)));
            String html = convertToHtml(content);
            String outputFilePath = inputFilePath.replace(".odoc", ".html");
            Files.write(Paths.get(outputFilePath), html.getBytes());
            System.out.println("Archivo convertido a: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String convertToHtml(String content) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Document</title></head><body>");

        // Patterns for matching odoc syntax
        Pattern header1Pattern = Pattern.compile("^#\\s+(.*)$", Pattern.MULTILINE);
        Pattern header2Pattern = Pattern.compile("^\\$\\s+(.*)$", Pattern.MULTILINE);
        Pattern customHeaderPattern = Pattern.compile("^\\?\\$\\s+(.*)$", Pattern.MULTILINE);
        Pattern ulItemPattern = Pattern.compile("^\\-\\s+(.*)$", Pattern.MULTILINE);
        Pattern olItemPattern = Pattern.compile("^\\*\\s+(.*)$", Pattern.MULTILINE);
        Pattern termPattern = Pattern.compile("^\\[(.*?)\\]\\((.*?)\\)$", Pattern.MULTILINE);
        Pattern linkPattern = Pattern.compile("^\\[(.*?)\\]\\{(.*?)\\}$", Pattern.MULTILINE);
        Pattern stylesheetPattern = Pattern.compile("^!\\{(.*?)\\}$", Pattern.MULTILINE);
        Pattern italicPattern = Pattern.compile("/(.*?)/");
        Pattern boldPattern = Pattern.compile("\\*(.*?)\\*");
        Pattern underlinedPattern = Pattern.compile("_(.*?)_");
        Pattern codeBlockPattern = Pattern.compile("\\\\python\\n(.*?)\\\\", Pattern.DOTALL);

        // Replace patterns with corresponding HTML
        content = header1Pattern.matcher(content).replaceAll("<h1>$1</h1>");
        content = header2Pattern.matcher(content).replaceAll("<h2>$1</h2>");
        content = customHeaderPattern.matcher(content).replaceAll("<h3 style='custom'>$1</h3>");
        content = ulItemPattern.matcher(content).replaceAll("<ul><li>$1</li></ul>");
        content = olItemPattern.matcher(content).replaceAll("<ol><li>$1</li></ol>");
        content = termPattern.matcher(content).replaceAll("<dt>$1</dt><dd>$2</dd>");
        content = linkPattern.matcher(content).replaceAll("<a href='$2'>$1</a>");
        content = stylesheetPattern.matcher(content).replaceAll("<link rel='stylesheet' href='$1'/>");
        content = italicPattern.matcher(content).replaceAll("<i>$1</i>");
        content = boldPattern.matcher(content).replaceAll("<b>$1</b>");
        content = underlinedPattern.matcher(content).replaceAll("<u>$1</u>");
        content = codeBlockPattern.matcher(content).replaceAll("<pre><code>$1</code></pre>");

        html.append(content);
        html.append("</body></html>");

        return html.toString();
    }
}
