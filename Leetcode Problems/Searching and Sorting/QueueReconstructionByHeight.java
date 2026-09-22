import java.util.*;

public class QueueReconstructionByHeight {
    public static int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(b[0], a[0]);
            }
            return Integer.compare(a[1], b[1]);
        });

        List<int[]> queue = new ArrayList<>();

        for (int[] person : people) {
            queue.add(person[1], person);
        }

        return queue.toArray(new int[queue.size()][]);
    }

    public static void main(String[] args) {
        int[][] people = {
            {7, 0}, {4, 4}, {7, 1},
            {5, 0}, {6, 1}, {5, 2}
        };

        int[][] result = reconstructQueue(people);

        for (int[] person : result) {
            System.out.println(Arrays.toString(person));
        }
    }
}
