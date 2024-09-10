package com.orangecat.utiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utiles {

    public static List<String> toList(String[] list) {
        return new ArrayList<>(Arrays.asList(list));
    }
}
