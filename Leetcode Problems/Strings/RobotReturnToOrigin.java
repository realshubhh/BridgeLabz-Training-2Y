public class RobotReturnToOrigin {

    public boolean judgeCircle(String moves) {
        int horizontal = 0;
        int vertical = 0;

        for (char move : moves.toCharArray()) {
            switch (move) {
                case 'R':
                    horizontal++;
                    break;
                case 'L':
                    horizontal--;
                    break;
                case 'U':
                    vertical++;
                    break;
                case 'D':
                    vertical--;
                    break;
                default:
                    break;
            }
        }

        return horizontal == 0 && vertical == 0;
    }

    public static void main(String[] args) {
        String moves = "UD";

        RobotReturnToOrigin solution = new RobotReturnToOrigin();
        boolean result = solution.judgeCircle(moves);

        System.out.println("Does the robot return to origin? " + result);
    }
}
