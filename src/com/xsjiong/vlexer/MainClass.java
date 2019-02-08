package com.xsjiong.vlexer;

import java.util.Arrays;

public class MainClass {
	private static char[] S = "this.println('hello');".toCharArray();
	private static VLexer lexer;

	public static void main(String[] args) {
		lexer = new VJavaLexer(S);
		lexer.parseAll();
		for (int i = 1; i <= lexer.getPartCount(); i++)
			System.out.println(lexer.getTypeName(lexer.getPartType(i)) + ":" + lexer.getPartText(i));
		insertString(1, "qwe");
		System.out.println(new String(S));
		for (int i = 1; i <= lexer.getPartCount(); i++)
			System.out.println(lexer.getTypeName(lexer.getPartType(i)) + ":" + lexer.getPartText(i));
		System.out.println(Arrays.toString(lexer.getPartStarts()));
		System.out.println(Arrays.toString(lexer.getPartEnds()));
	}

	private static void insertString(int i, String s) {
		char[] cs = s.toCharArray();
		char[] ns = new char[cs.length + S.length];
		System.arraycopy(S, 0, ns, 0, i);
		System.arraycopy(cs, 0, ns, i, cs.length);
		System.arraycopy(S, i, ns, i + cs.length, S.length - i);
		S = ns;
		cs = null;
		ns = null;
		System.gc();
		lexer.onTextReferenceUpdate(S, S.length);
		lexer.onInsertChars(i, s.length());
	}
}