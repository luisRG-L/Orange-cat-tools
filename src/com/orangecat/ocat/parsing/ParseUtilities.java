package com.orangecat.ocat.parsing;

public class ParseUtilities {

    public static boolean inList(String[] list, String element) {
        boolean isInList = false;
        for (String listElement : list) {
            if (listElement.equals(element)) {
                isInList = true;
                break;
            }
        }
        return isInList;
    }
}
