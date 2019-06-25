package com.xsjiong.vlexer;

public class VXMLLexer extends VCommonLexer {
	@Override
	protected byte getNext() {
		if (P == L) return TYPE_EOF;
		ST = P;
		if (S[P] == ' ' || S[P] == '\t') ReadSpaces();
		if (D[DS[0]] == TYPE_CONTENT_START) {
			while (true) {
				while (P + 1 < L && (!(S[P] == '<' && S[P + 1] == '/'))) P++;
				int st = P;
				if ((P += 2) >= L) {
					P = st;
					return TYPE_CONTENT;
				}
				if (isIdentifierStart(S[P])) {
					ReadIdentifier();
					if (P == L) {
						P = st;
						return TYPE_CONTENT;
					}
					if (S[P] == '>') {
						P = st;
						return TYPE_CONTENT;
					}
				}
			}
			// TODO Check Me Here
		} else {
			if (isIdentifierStart(S[P])) {
				ReadIdentifier();
				return TYPE_IDENTIFIER;
			}
			switch (S[P++]) {
				case '>':
					return TYPE_CONTENT_START;
				case '?':
					if (P == L) return TYPE_TAG_END;
					if (S[P] == '>') P++;
					return TYPE_TAG_END;
				case '<': {
					if (P == L) return TYPE_TAG_START;
					if (isIdentifierStart(S[P])) ReadIdentifier();
					else switch (S[P++]) {
						case '?':
							if (isIdentifierStart(S[P])) ReadIdentifier();
							return TYPE_TAG_START;
						case '!':
							if (P == L) return TYPE_TAG_START;
							if (S[P++] == '-') {
								if (P == L) return TYPE_TAG_START;
								if (S[P++] == '-') {
									if (P == L) return TYPE_TAG_START;
									while (P + 2 < L && (!(S[P] == '-' && S[P + 1] == '-' && S[P + 2] == '>'))) P++;
									P = Math.min(P + 3, L);
									return TYPE_COMMENT;
								}
							}
							return TYPE_TAG_START;
						case '/':
							if (P == L) return TYPE_TAG_END;
							do P++;
							while (P != L && S[P] != '>');
							if (P != L) P++;
							return TYPE_TAG_END;
					}
					return TYPE_TAG_START;
				}
				case '=':
				case '"':
					return processSymbol(S[P - 1]);
			}
		}
		return UNRESOLVED_TYPE;
	}

	@Override
	protected byte getWordType(int st, int en) {
		return 0;
	}

	@Override
	public Trie getKeywordTrie() {
		return null;
	}

	@Override
	protected boolean isIdentifierStart(char c) {
		return Character.isJavaIdentifierStart(c);
	}

	@Override
	protected boolean isIdentifierPart(char c) {
		return Character.isJavaIdentifierPart(c);
	}

	@Override
	protected boolean isWhitespace(char c) {
		return (c == ' ' || c == '\n' || c == '\t' || c == '\r' || c == '\f' || c == '\uFFFF');
	}
}
