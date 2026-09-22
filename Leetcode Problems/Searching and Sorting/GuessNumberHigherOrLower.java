public class GuessNumberHigherOrLower {
    private int secretNumber;

    public GuessNumberHigherOrLower(int secretNumber) {
        this.secretNumber = secretNumber;
    }

    private int guess(int num) {
        if (num == secretNumber) {
            return 0;
        }
        return num > secretNumber ? -1 : 1;
    }

    public int guessNumber(int n) {
        int left = 1, right = n;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int result = guess(mid);

            if (result == 0) {
                return mid;
            } else if (result < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        GuessNumberHigherOrLower game = new GuessNumberHigherOrLower(6);
        System.out.println("Guessed number: " + game.guessNumber(10));
    }
}
