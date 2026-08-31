import java.util.Scanner;

public class Level2_Q1_EmployeeBonusCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final int NUM_EMPLOYEES = 10;

        double[] salary = new double[NUM_EMPLOYEES];
        double[] yearsOfService = new double[NUM_EMPLOYEES];
        double[] newSalary = new double[NUM_EMPLOYEES];
        double[] bonusAmount = new double[NUM_EMPLOYEES];

        double totalBonus = 0.0;
        double totalOldSalary = 0.0;
        double totalNewSalary = 0.0;

        for (int i = 0; i < NUM_EMPLOYEES; i++) {
            System.out.println("Employee " + (i + 1) + ":");
            System.out.print("  Enter salary: ");
            double sal = sc.nextDouble();
            System.out.print("  Enter years of service: ");
            double years = sc.nextDouble();

            if (sal < 0 || years < 0) {
                System.out.println("  Invalid input. Please enter again.");
                i--;
                continue;
            }
            salary[i] = sal;
            yearsOfService[i] = years;
        }

        for (int i = 0; i < NUM_EMPLOYEES; i++) {
            double bonusPercent = (yearsOfService[i] > 5) ? 0.05 : 0.02;
            bonusAmount[i] = salary[i] * bonusPercent;
            newSalary[i] = salary[i] + bonusAmount[i];

            totalBonus += bonusAmount[i];
            totalOldSalary += salary[i];
            totalNewSalary += newSalary[i];
        }

        System.out.println("\nTotal Bonus Payout: " + totalBonus);
        System.out.println("Total Old Salary: " + totalOldSalary);
        System.out.println("Total New Salary: " + totalNewSalary);
        sc.close();
    }
}
