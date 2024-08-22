package com.orangecat.odoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * The {@code OdocToHtml} class contains the 'odoc command'
 */
public class OdocToHtml {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Use: odoc <input_file>.odoc [-o <output_file>.oss]");
            System.exit(1);
        }

        String inputFilePath = args[0];
        String outputHtmlPath = inputFilePath.replace(".odoc", ".html");

        try {
            String content = new String(Files.readAllBytes(Paths.get(inputFilePath)));
            String html = convertToHtml(content);
            Files.write(Paths.get(outputHtmlPath), html.getBytes());
            System.out.println("Archivo convertido a: " + outputHtmlPath);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + inputFilePath);
        }

        if (args.length > 2 && args[1].equals("-o")) {
            String ossFilePath = args[2];
            try {
                String content = new String(Files.readAllBytes(Paths.get(ossFilePath)));
                String css = convertToCss(content);
                String outputCssPath = ossFilePath.replace(".oss", ".css");
                Files.write(Paths.get(outputCssPath), css.getBytes());
                System.out.println("Archivo convertido a: " + outputCssPath);
            } catch (IOException e) {
                System.err.println("Error al leer el archivo: " + ossFilePath);
            }
        }
    }

    private static String convertToCss(String content) {
		StringBuilder css = new StringBuilder();
		css.append("/* Compiled with Orange Cat documentation compiler (OCD compiler)*/\n");
		css.append("* { display: inline-block; }");
	
		String[] tokens = content.split("\\s+");
		int tknPos = 0;
	
		while (tknPos < tokens.length) {
			String tag = tokens[tknPos];
			String classDec = "predef";
			String element = "*";
			String id = "";
			Map<String, String> properties = new HashMap<>();
	
			tknPos++;
			if (tknPos < tokens.length && tokens[tknPos].startsWith(":")) {
				classDec = tokens[tknPos].substring(1);
				tknPos++;
			}
			if (tknPos < tokens.length && tokens[tknPos].startsWith(".")) {
				element = tokens[tknPos].substring(1);
				tknPos++;
			}
			if (tknPos < tokens.length && tokens[tknPos].startsWith("#")) {
				id = tokens[tknPos].substring(1);
				tknPos++;
			}
			if (tknPos < tokens.length && tokens[tknPos].startsWith("{")) {
				tknPos++;
				while (tknPos < tokens.length && !tokens[tknPos].equals("}")) {
					String property = tokens[tknPos].replace(":", "");
					tknPos++;
					if (tknPos < tokens.length) {
						String value = tokens[tknPos];
						properties.put(property, value);
					}
					tknPos++;
				}
			}
	
			StringBuilder cssSentence = new StringBuilder();
	
			// Tag sentences
			switch (tag) {
				case "txt" -> cssSentence.append("*");
				case "h1" -> cssSentence.append("h1");
				case "h2" -> cssSentence.append("h2");
				case "hc" -> cssSentence.append("span.custom");
				default -> cssSentence.append(tag);
			}
	
			// Construct the CSS selector
			if (!element.equals("*")) {
				cssSentence.append(element);
			}
			if (!id.isEmpty()) {
				cssSentence.append("#").append(id);
			}
			if (!classDec.equals("predef")) {
				cssSentence.append(".").append(classDec);
			}
	
			cssSentence.append(" {");
	
			// Add CSS properties
			properties.forEach((key, value) -> {
				String cssProperty = key.replace("text-color", "color")
										.replace("border-mark", "border-style")
										.replace("border: 1px solid;border-color", "border-color");
				cssSentence.append("\n\t").append(cssProperty).append(": ").append(value).append(";");
			});
	
			cssSentence.append("\n}\n");
			css.append(cssSentence);
			tknPos++;
		}
	
		return css.toString();
	}
	

    private static String convertToHtml(String content) {
        // Patrones para coincidir con la sintaxis odoc
        Pattern titlePattern = Pattern.compile("^#\\s+(.*)$", Pattern.MULTILINE);
        Pattern header1Pattern = Pattern.compile("^\\$\\s+(.*)$", Pattern.MULTILINE);
        Pattern header2Pattern = Pattern.compile("^\\$\\$\\s+(.*)$", Pattern.MULTILINE);
        Pattern customHeaderPattern = Pattern.compile("^\\?\\$\\s+(.*)$", Pattern.MULTILINE);
        Pattern ulItemPattern = Pattern.compile("^-\\s+(.*)$", Pattern.MULTILINE);
        Pattern olStartPattern = Pattern.compile("^1\\.\\s+(.*)$", Pattern.MULTILINE);
        Pattern olItemPattern = Pattern.compile("^\\d+\\.\\s+(.*)$", Pattern.MULTILINE);
        Pattern olEndPattern = Pattern.compile("^\\*\\s+(.*)$", Pattern.MULTILINE);
        Pattern termPattern = Pattern.compile("^\\[(.*?)\\]\\((.*?)\\)$", Pattern.MULTILINE);
        Pattern linkPattern = Pattern.compile("^\\[(.*?)\\]\\{(.*?)\\}$", Pattern.MULTILINE);
        Pattern stylesheetPattern = Pattern.compile("^!\\{(.*?)\\}$", Pattern.MULTILINE);
        Pattern classPatternStart = Pattern.compile("^&\\s+(.*)$", Pattern.MULTILINE);
        Pattern classPatternEnd = Pattern.compile("^%", Pattern.MULTILINE);
        Pattern italicPattern = Pattern.compile("\\\\(.*?)\\\\");
        Pattern boldPattern = Pattern.compile("\\*(.*?)\\*");
        Pattern underlinedPattern = Pattern.compile("_(.*?)_");
        Pattern codeBlockPattern = Pattern.compile("\\\\ocat\\n(.*?)\\\\", Pattern.DOTALL);

        // Reemplazar patrones con el HTML correspondiente
        content = titlePattern.matcher(content).replaceAll("<title>$1</title><h1>$1</h1>");
        content = header1Pattern.matcher(content).replaceAll("<h2>$1</h2>");
        content = header2Pattern.matcher(content).replaceAll("<h3>$1</h3>");
        content = customHeaderPattern.matcher(content).replaceAll("<span style='custom'>$1</span>");
        content = ulItemPattern.matcher(content).replaceAll("<ul><li>$1</li></ul>");
        content = olStartPattern.matcher(content).replaceAll("<ol><li>$1</li>");
        content = olItemPattern.matcher(content).replaceAll("<li>$1</li>");
        content = olEndPattern.matcher(content).replaceAll("</ol>");
        content = termPattern.matcher(content).replaceAll("<dt>$1</dt><dd>$2></dd>");
        content = linkPattern.matcher(content).replaceAll("<a href='$2'>$1</a>");
        content = stylesheetPattern.matcher(content).replaceAll("<link rel='stylesheet' href='./$1'/>");
        content = italicPattern.matcher(content).replaceAll("<em>$1</em>");
        content = boldPattern.matcher(content).replaceAll("<strong>$1</strong>");
        content = underlinedPattern.matcher(content).replaceAll("<u>$1</u>");
        content = codeBlockPattern.matcher(content).replaceAll("<pre><code>$1</code></pre>");
        content = classPatternStart.matcher(content).replaceAll("<div class='$1'>");
        content = classPatternEnd.matcher(content).replaceAll("</div>");

        content = content.replace(".oss", ".css");
        content = content.replace(".odoc", ".html");

        // Construir HTML completo con tabulación y saltos de línea
        StringBuilder formattedHtml = new StringBuilder();
        formattedHtml.append("<html>\n");
        formattedHtml.append("<body>\n");

        String[] lines = content.split("\n");
        for (String line : lines) {
            formattedHtml.append("\t").append(line).append("\n");
        }

        formattedHtml.append("</body>\n");
        formattedHtml.append("</html>\n");

        return formattedHtml.toString();
    }
}
