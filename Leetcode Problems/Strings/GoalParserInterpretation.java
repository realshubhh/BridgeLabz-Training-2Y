public class GoalParserInterpretation {

    public String interpret(String command) {
        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < command.length()) {
            if (command.charAt(i) == 'G') {
                result.append('G');
                i++;
            } else if (command.charAt(i + 1) == ')') {
                result.append('o');
                i += 2;
            } else {
                result.append("al");
                i += 4;
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String command = "G()(al)";

        GoalParserInterpretation solution = new GoalParserInterpretation();
        String result = solution.interpret(command);

        System.out.println("Interpreted string: " + result);
    }
}
