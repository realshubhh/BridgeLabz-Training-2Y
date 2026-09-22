public class RansomNote {

    public boolean canConstruct(String ransomNote, String magazine) {
        int[] letterCounts = new int[26];

        for (char c : magazine.toCharArray()) {
            letterCounts[c - 'a']++;
        }

        for (char c : ransomNote.toCharArray()) {
            letterCounts[c - 'a']--;
            if (letterCounts[c - 'a'] < 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        String ransomNote = "aa";
        String magazine = "aab";

        RansomNote solution = new RansomNote();
        boolean result = solution.canConstruct(ransomNote, magazine);

        System.out.println("Can the ransom note be constructed? " + result);
    }
}
