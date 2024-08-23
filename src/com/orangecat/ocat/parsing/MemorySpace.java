package com.orangecat.ocat.parsing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemorySpace {
    public Map<String, String> variables = new HashMap<>();
    public Map<String, List<String>> functions = new HashMap<>();

    public void addVariable(String name, String value) {
        variables.put(name, value);
    }

    public void addFunction(String name, List<String> string) {
        functions.put(name, string);
    }
}
