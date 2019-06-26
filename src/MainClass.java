import com.xsjiong.vlexer.VJavaLexer;
import com.xsjiong.vlexer.VLexer;

public class MainClass {
	private static char[] S = "public static                      ".toCharArray();
	private static VLexer lexer;

	public static void main(String[] args) {
		lexer = new VJavaLexer();
		lexer.setText(S);
		printState();
		 /*for (int i = 1; i <= lexer.getPartCount(); i++)
			System.out.println(lexer.getTypeName(lexer.getPartType(i)) + ":" + lexer.getPartText(i));
		insertString(1, ".");
		deleteString(2, 2);
		System.out.println(new String(S));
		for (int i = 1; i <= lexer.getPartCount(); i++)
			System.out.println(lexer.getTypeName(lexer.getPartType(i)) + ":" + lexer.getPartText(i));
		System.out.println(Arrays.toString(lexer.getPartStarts()));
		System.out.println(Arrays.toString(lexer.getPartStarts()));*/
	}

	private static void printState() {
		int t;
		for (int i = 1; i <= lexer.DS[0]; i++) {
			if (i == lexer.DS[0]) t = lexer.L;
			else t = lexer.DS[i + 1];
			System.out.println(lexer.getTypeName(lexer.D[i]) + ":" + new String(S, lexer.DS[i], t - lexer.DS[i]));
		}
		System.out.println("============");
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

	private static void deleteString(int i, int len) {
		if (len > i) len = i;
		char[] ns = new char[S.length - len];
		System.arraycopy(S, 0, ns, 0, i - len);
		System.arraycopy(S, i, ns, i - len, S.length - i);
		S = ns;
		ns = null;
		System.gc();
		lexer.onTextReferenceUpdate(S, S.length);
		lexer.onDeleteChars(i, len);
	}
}
