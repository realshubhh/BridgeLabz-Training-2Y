public class NumberOfLinesToWriteString {

    public int[] numberOfLines(int[] widths, String s) {
        int lines = 1;
        int currentLineWidth = 0;

        for (char c : s.toCharArray()) {
            int charWidth = widths[c - 'a'];

            if (currentLineWidth + charWidth > 100) {
                lines++;
                currentLineWidth = charWidth;
            } else {
                currentLineWidth += charWidth;
            }
        }

        return new int[] {lines, currentLineWidth};
    }

    public static void main(String[] args) {
        int[] widths = {
            10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
            10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
            10, 10, 10, 10, 10, 10
        };
        String s = "abcdefghijklmnopqrstuvwxyz";

        NumberOfLinesToWriteString solution = new NumberOfLinesToWriteString();
        int[] result = solution.numberOfLines(widths, s);

        System.out.println("Lines used: " + result[0] + ", width of last line: " + result[1]);
    }
}
