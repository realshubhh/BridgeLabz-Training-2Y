import java.util.HashMap;
import java.util.Map;

public class IsomorphicStrings {

    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        Map<Character, Character> mapStoT = new HashMap<>();
        Map<Character, Character> mapTtoS = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char charS = s.charAt(i);
            char charT = t.charAt(i);

            if (mapStoT.containsKey(charS) && mapStoT.get(charS) != charT) {
                return false;
            }

            if (mapTtoS.containsKey(charT) && mapTtoS.get(charT) != charS) {
                return false;
            }

            mapStoT.put(charS, charT);
            mapTtoS.put(charT, charS);
        }

        return true;
    }

    public static void main(String[] args) {
        String s = "egg";
        String t = "add";

        IsomorphicStrings solution = new IsomorphicStrings();
        boolean result = solution.isIsomorphic(s, t);

        System.out.println("Are the strings isomorphic? " + result);
    }
}
