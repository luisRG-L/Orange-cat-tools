package com.orangecat.ocat.parsing;

import java.util.HashMap;
import java.util.Map;

public class MemorySpace {
    public Map<String, String> variables = new HashMap<>();

    public void addVariable(String name, String value) {
        variables.put(name, value);
    }
}
