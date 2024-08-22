package com.orangecat.ocat;

import com.orangecat.ocat.errors.SyntaxError;
import com.orangecat.ocat.parsing.*;

public class Lexer {
	private final String[] keywords;
	private String token;
	private int token_pos = 0;
	private int breakpoint_index = 0;
	private MemorySpace space = new MemorySpace();

	public Lexer(String[] keyWords) {
		this.keywords = keyWords;
		this.parseCode();
    }

	public void parseCode() {
		token = keywords[token_pos];
		while(token_pos < keywords.length) {
			if (ParseConverter.isVartype(token)) {
				ParseFunctions.parseVariableDeclaration(this);
			} else if (ParseConverter.isPrint(token)) {
				ParseFunctions.parsePrintStatement(this);
			}else if (ParseConverter.isBreakpoint(token)) {
				breakpoint_index ++;
			} else {
				SyntaxError.undefined(token, getTokenIndex(), getBreakpointIndex());
			}
			nextToken();
		}
		System.exit(0);
	}

	public void nextToken() {
		if (++ token_pos >= keywords.length) { ///< Verify EOD
			System.exit(0);
		}
		token = keywords[token_pos];
	}

	public String getToken() {
		return token;
	}

	public int getTokenIndex() {
		return token_pos;
	}

	public int getBreakpointIndex() {
		return breakpoint_index;
	}

	public MemorySpace getMemorySpace() {
		return space;
	}
}
