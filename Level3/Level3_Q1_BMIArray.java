import java.util.Scanner;

public class Level3_Q1_BMIArray {

    public static String[] computeBMIStatus(double weight, double height) {
        double heightInMeters = height / 100.0;
        double bmi = weight / (heightInMeters * heightInMeters);
        String status;

        if (bmi <= 18.4) {
            status = "Underweight";
        } else if (bmi <= 24.9) {
            status = "Normal";
        } else if (bmi <= 39.9) {
            status = "Overweight";
        } else {
            status = "Obese";
        }

        return new String[]{String.valueOf(height), String.valueOf(weight), String.format("%.2f", bmi), status};
    }

    public static String[][] buildResultTable(double[][] personData) {
        String[][] result = new String[personData.length][4];
        for (int i = 0; i < personData.length; i++) {
            double weight = personData[i][0];
            double height = personData[i][1];
            result[i] = computeBMIStatus(weight, height);
        }
        return result;
    }

    public static void displayResultTable(String[][] result) {
        System.out.println("\nHeight(cm)\tWeight(kg)\tBMI\tStatus");
        for (String[] row : result) {
            System.out.println(row[0] + "\t\t" + row[1] + "\t\t" + row[2] + "\t" + row[3]);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final int TEAM_SIZE = 10;
        double[][] personData = new double[TEAM_SIZE][2];

        for (int i = 0; i < TEAM_SIZE; i++) {
            System.out.print("Enter weight (kg) of person " + (i + 1) + ": ");
            personData[i][0] = sc.nextDouble();
            System.out.print("Enter height (cm) of person " + (i + 1) + ": ");
            personData[i][1] = sc.nextDouble();
        }

        String[][] result = buildResultTable(personData);
        displayResultTable(result);
        sc.close();
    }
}
