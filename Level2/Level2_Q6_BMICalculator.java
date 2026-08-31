import java.util.Scanner;

public class Level2_Q6_BMICalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of persons: ");
        int n = sc.nextInt();

        double[] weight = new double[n];
        double[] height = new double[n];
        double[] bmi = new double[n];
        String[] status = new String[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter weight (kg) of person " + (i + 1) + ": ");
            weight[i] = sc.nextDouble();
            System.out.print("Enter height (cm) of person " + (i + 1) + ": ");
            height[i] = sc.nextDouble();
        }

        for (int i = 0; i < n; i++) {
            double heightInMeters = height[i] / 100.0;
            bmi[i] = weight[i] / (heightInMeters * heightInMeters);

            if (bmi[i] <= 18.4) {
                status[i] = "Underweight";
            } else if (bmi[i] <= 24.9) {
                status[i] = "Normal";
            } else if (bmi[i] <= 39.9) {
                status[i] = "Overweight";
            } else {
                status[i] = "Obese";
            }
        }

        System.out.println("\nHeight(cm)\tWeight(kg)\tBMI\tStatus");
        for (int i = 0; i < n; i++) {
            System.out.printf("%.1f\t\t%.1f\t\t%.2f\t%s%n", height[i], weight[i], bmi[i], status[i]);
        }
        sc.close();
    }
}
