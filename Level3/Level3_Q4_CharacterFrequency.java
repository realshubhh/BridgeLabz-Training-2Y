import java.util.Scanner;

public class Level3_Q4_CharacterFrequency {

    public static String[][] findCharacterFrequency(String text) {
        int[] frequency = new int[256];

        for (int i = 0; i < text.length(); i++) {
            frequency[text.charAt(i)]++;
        }

        String[][] result = new String[text.length()][2];
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            result[i][0] = String.valueOf(c);
            result[i][1] = String.valueOf(frequency[c]);
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String text = sc.nextLine();

        String[][] result = findCharacterFrequency(text);

        System.out.println("Character\tFrequency");
        for (int i = 0; i < text.length(); i++) {
            System.out.println(result[i][0] + "\t\t" + result[i][1]);
        }
        sc.close();
    }
}
