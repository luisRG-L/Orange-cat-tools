package com.orangecat.ocat;

import com.orangecat.ocat.errors.SyntaxError;
import com.orangecat.ocat.parsing.*;

public class Lexer {
	private final String[] keywords;
	private String token;
	private int token_pos = 0;
	private int breakpoint_index = 0;
	private final MemorySpace space = new MemorySpace();
	public boolean qualified = false;
	public boolean lastConditionalValue = false;

	public Lexer(String[] keyWords) {
		this.keywords = keyWords;
		this.parseCode();
    }

	public void parseCode() {
		token = keywords[token_pos];
		while(token_pos < keywords.length) {
			if (ParseConverter.isVartype(token)) {
				ParseFunctions.parseVariableDeclaration(this);
			} else {
                assert token != null;
				if (ParseConverter.isPrint(token)) {
					ParseFunctions.parsePrintStatement(this);
				} else if (ParseConverter.isWarn(token)) {
					ParseFunctions.parseWarnStatement(this);
				} else if (ParseConverter.isError(token)) {
					ParseFunctions.parsePrintStatement(this);
				} else if (ParseConverter.isBreakpoint(token)) {
                    breakpoint_index ++;
                } else if (ParseConverter.isFunctionDeclaration(token)) {
                    ParseFunctions.parseFunctionDeclaration(this);
                } else if (ParseConverter.isFunctionCall(token)) {
                    ParseFunctions.parseFunctionCall(this);
                } else if (ParseConverter.isIf(token)) {
					ParseFunctions.parseIfConditional(this);
				} else if (ParseConverter.isElse(token)) {
					ParseFunctions.parseElseConditional(this);
				} else if (ParseConverter.isImport(token)) {
					ParseFunctions.parseImport(this);
				} else if (ParseConverter.isTest(token)) {
					ParseFunctions.parseTest(this);
				} else if (token.equals(";")) {
					breakpoint_index ++;
				}  else if (token.equals("\\s+")) {
					breakpoint_index ++;
				} else if (token.equals("{")) {
					qualified = false;
				} else {
					SyntaxError.undefined(token, getTokenIndex(), getBreakpointIndex());
                }
            }
			nextToken();
		}
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
