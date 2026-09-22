public class ExcelSheetColumnTitle {

    public String convertToTitle(int columnNumber) {
        StringBuilder title = new StringBuilder();

        while (columnNumber > 0) {
            columnNumber--;
            char letter = (char) ('A' + (columnNumber % 26));
            title.append(letter);
            columnNumber /= 26;
        }

        return title.reverse().toString();
    }

    public static void main(String[] args) {
        int columnNumber = 701;

        ExcelSheetColumnTitle solution = new ExcelSheetColumnTitle();
        String result = solution.convertToTitle(columnNumber);

        System.out.println("Excel column title: " + result);
    }
}
