package com.orangecat.ocat.parsing;

import com.orangecat.ocat.typing.*;

public class ParseConverter {
    public static boolean isVartype(String token) {
        return (
                ParseUtilities.inList(Types.VARTYPES, token)
                );
    }
}
