package com.xsjiong.vlexer;

public class VNullLexer extends VLexer {
	@Override
	protected byte getNext() {
		if (P == L) return VLexer.EOF;
		P = L;
		return VLexer.TYPE_PURE;
	}

	@Override
	protected boolean isWhitespace(char c) {
		return false;
	}

	public VNullLexer() {
	}
}
