package com.orangecat.ocat.http;

import com.orangecat.utiles.Utiles;

public class HTTPUtilities {
    public static String parseHTTPTag(String ss) {
        StringBuilder htmlOutput = new StringBuilder();
        String [] httpTags = ss.split(">");
        for (String httpTag : httpTags) {
            if (httpTag.startsWith("\"")) {
                htmlOutput.append(httpTag.replace("\"", "").replace("%", " "));
            }else {
                htmlOutput.append(getTag(httpTag));
            }
        }
        for (String httpTag : httpTags) {
            if (!httpTag.startsWith("\"")) {
                htmlOutput.append(getTag(httpTag, false));
            }
        }
        return htmlOutput.toString();
    }

    public static String getTag(String tag) {
        return getTag(tag, true);
    }

    public static String getTag(String tag, boolean p) {
        String tagName = switch (tag) {
            case "paragraph" -> "p";
            case "title" -> "title";
            case "group" -> "div";
            case "section" -> "section";
            case "article" -> "article";
            default -> tag;
        };
        return p ? "<"+tagName+">" : "</"+tagName+">";
    }
}
