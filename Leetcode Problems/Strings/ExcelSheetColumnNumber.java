public class ExcelSheetColumnNumber {

    public int titleToNumber(String columnTitle) {
        int result = 0;

        for (char c : columnTitle.toCharArray()) {
            int value = c - 'A' + 1;
            result = result * 26 + value;
        }

        return result;
    }

    public static void main(String[] args) {
        String columnTitle = "ZY";

        ExcelSheetColumnNumber solution = new ExcelSheetColumnNumber();
        int result = solution.titleToNumber(columnTitle);

        System.out.println("Excel column number: " + result);
    }
}
