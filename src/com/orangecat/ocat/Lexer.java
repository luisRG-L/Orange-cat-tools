package com.orangecat.ocat;

import com.orangecat.ocat.parsing.ParseConverter;

public class Lexer {
	private final String[] keywords;
	private String token;
	private int token_pos = 0;

	public Lexer(String[] keyWords) {
		this.keywords = keyWords;
		this.parseCode();
    }

	private void parseCode() {
		while(token_pos < keywords.length) {
			if (ParseConverter.isVartype(token)) {
				ParseFunctions.parseVariableDeclaration(this);
			}
			nextToken();
		}
	}

	private void nextToken() {
		if (++ token_pos >= keywords.length) { ///< Verify EOD
			token_pos --;
		}
		token = keywords[token_pos];
	}
}
