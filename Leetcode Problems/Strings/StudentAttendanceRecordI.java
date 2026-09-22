public class StudentAttendanceRecordI {

    public boolean checkRecord(String s) {
        int absentCount = 0;
        int consecutiveLateCount = 0;

        for (char c : s.toCharArray()) {
            if (c == 'A') {
                absentCount++;
                if (absentCount >= 2) {
                    return false;
                }
            }

            if (c == 'L') {
                consecutiveLateCount++;
                if (consecutiveLateCount >= 3) {
                    return false;
                }
            } else {
                consecutiveLateCount = 0;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        String s = "PPALLP";

        StudentAttendanceRecordI solution = new StudentAttendanceRecordI();
        boolean result = solution.checkRecord(s);

        System.out.println("Eligible for attendance award? " + result);
    }
}
