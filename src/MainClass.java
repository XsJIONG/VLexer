import com.xsjiong.vlexer.VJavaLexer;
import com.xsjiong.vlexer.VLexer;

public class MainClass {
	private static char[] S = ("import com.sun.istack.internal.Nullable;\n" +
			"\n" +
			"import java.util.ArrayList;\n" +
			"import java.util.List;\n" +
			"\n" +
			"public class WaveView extends View {\n" +
			"\tprivate static int centerColor;\n" +
			"\tprivate Paint mPaint;\n" +
			"\tprivate float alpharate;\n" +
			"\tprivate boolean isStartAnim = false;\n" +
			"\tprivate int mSpace;\n" +
			"\tprivate int mWidth;\n" +
			"\tprivate boolean isFillstyle;\n" +
			"\tprivate List<Float> mCount = new ArrayList();\n" +
			"\tprivate List<Integer> mAlphas = new ArrayList();\n" +
			"\n" +
			"\n" +
			"\tpublic WaveView(Context context) {\n" +
			"\t\tthis(context, null);\n" +
			"\t}\n" +
			"\n" +
			"\tpublic WaveView(Context context, @Nullable AttributeSet attrs) {\n" +
			"\t\tthis(context, attrs, 0);\n" +
			"\n" +
			"\t}\n" +
			"\n" +
			"\tpublic WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {\n" +
			"\t\tsuper(context, attrs, defStyleAttr);\n" +
			"\t\tTypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveView, defStyleAttr, 0);\n" +
			"\t\tcenterColor = typedArray.getColor(R.styleable.WaveView_wavecolor, getResources().getColor(R.color.colorAccent));\n" +
			"\t\tmSpace = typedArray.getInteger(R.styleable.WaveView_space, 100);\n" +
			"\t\tisFillstyle = typedArray.getBoolean(R.styleable.WaveView_fillstyle, true);\n" +
			"\t\tmWidth = typedArray.getInteger(R.styleable.WaveView_width, 400);\n" +
			"\t\ttypedArray.recycle();\n" +
			"\t\tinit();\n" +
			"\t}\n" +
			"\n" +
			"\tprivate void init() {\n" +
			"\t\tmPaint = new Paint();\n" +
			"\t\tmPaint.setAntiAlias(true);\n" +
			"\t\tif (isFillstyle) {\n" +
			"\t\t\tmPaint.setStyle(Paint.Style.FILL);\n" +
			"\t\t} else {\n" +
			"\t\t\tmPaint.setStyle(Paint.Style.STROKE);\n" +
			"\t\t\tmPaint.setStrokeWidth(3);\n" +
			"\t\t}\n" +
			"\t\talpharate = 255f / mWidth; //注意这里 如果为int类型就会为0,除数中f一定要加,默认int ;\n" +
			"\t\tmAlphas.add(255);\n" +
			"\t\tmCount.add(0f);\n" +
			"\t}\n" +
			"\n" +
			"\n" +
			"\t@Override\n" +
			"\tprotected void onDraw(Canvas canvas) {\n" +
			"\t\tif (isStartAnim) {\n" +
			"\t\t\tinvalidate();\n" +
			"\t\t}\n" +
			"\t\tmPaint.setColor(centerColor);\n" +
			"\t\tfor (int i = 0; i < mCount.size(); i++) { //遍历圆数目\n" +
			"\t\t\tInteger cuAlpha = mAlphas.get(i);\n" +
			"\t\t\tmPaint.setAlpha(cuAlpha);\n" +
			"\t\t\tFloat aFloat = widths.get(i);\n" +
			"\t\t\tcanvas.drawCircle(getWidth() / 2, getHeight() / 2, aFloat, mPaint); //画圆\n" +
			"\t\t\tif (aFloat < mWidth) {  //扩散直径和透明度\n" +
			"\t\t\t\tmAlphas.set(i, (int) (255 - alpharate * aFloat));\n" +
			"\t\t\t\tmCount.set(i, aFloat + 1);\n" +
			"\t\t\t}\n" +
			"\n" +
			"\t\t}\n" +
			"\n" +
			"\t\tif (widths.size() >= 5) {\n" +
			"\t\t\tmAlphas.remove(0);\n" +
			"\t\t\tmCount.remove(0);\n" +
			"\t\t}\n" +
			"\n" +
			"\t\tif (widths.get(widths.size() - 1) == mSpace) {\n" +
			"\t\t\tmAlphas.add(255);\n" +
			"\t\t\tmCount.add(0f);\n" +
			"\t\t}\n" +
			"\n" +
			"\t}\n" +
			"\n" +
			"\n" +
			"\tpublic void startAnim() {\n" +
			"\t\tisStartAnim = true;\n" +
			"\t\tinvalidate();\n" +
			"\t}\n" +
			"\n" +
			"\tpublic void pauseAnim() {\n" +
			"\t\tisStartAnim = false;\n" +
			"\t}\n" +
			"\n" +
			"}").toCharArray();
	private static VLexer lexer;

	public static void main(String[] args) {
		lexer = new VJavaLexer();
		lexer.setText(S);
		insertString(1, "q");
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
