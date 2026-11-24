import java.util.*;

/**
 * Q7 - Singly Linked List: Social Media Friend Connections
 * Outer list : users (userId, name, age)  -> singly linked
 * Inner list : each user's friend IDs     -> nested singly linked list
 * Friendship is mutual: adding A-B adds B to A's list AND A to B's list.
 */
public class FriendConnections {

    static class FriendNode {
        int friendId; FriendNode next;
        FriendNode(int id) { friendId = id; }
    }

    static class User {
        int id; String name; int age;
        FriendNode friends;            // head of nested friend list
        int friendCount;
        User next;
        User(int id, String name, int age) { this.id = id; this.name = name; this.age = age; }
        @Override public String toString() { return String.format("ID: %-3d | %-8s | Age: %d", id, name, age); }
    }

    private User head, tail;

    // ---------- users
    private User findById(int id) {
        for (User u = head; u != null; u = u.next) if (u.id == id) return u;
        return null;
    }

    public boolean addUser(int id, String name, int age) {
        if (findById(id) != null) { System.out.println("  [!] User ID " + id + " already exists."); return false; }
        User u = new User(id, name, age);
        if (tail == null) head = tail = u; else { tail.next = u; tail = u; }
        return true;
    }

    // ---------- nested-list helpers
    private boolean hasFriend(User u, int fid) {
        for (FriendNode f = u.friends; f != null; f = f.next) if (f.friendId == fid) return true;
        return false;
    }

    private void insertFriend(User u, int fid) {                // insert at head of nested list, O(1)
        FriendNode f = new FriendNode(fid);
        f.next = u.friends; u.friends = f; u.friendCount++;
    }

    private boolean deleteFriend(User u, int fid) {
        FriendNode prev = null, cur = u.friends;
        while (cur != null && cur.friendId != fid) { prev = cur; cur = cur.next; }
        if (cur == null) return false;
        if (prev == null) u.friends = cur.next; else prev.next = cur.next;
        cur.next = null;
        u.friendCount--;
        return true;
    }

    // ---------- 1. add connection
    public boolean addFriendship(int a, int b) {
        User ua = findById(a), ub = findById(b);
        if (ua == null || ub == null) { System.out.println("  [!] Both users must exist (" + a + ", " + b + ")."); return false; }
        if (a == b) { System.out.println("  [!] A user cannot befriend themselves."); return false; }
        if (hasFriend(ua, b)) { System.out.println("  [!] " + ua.name + " and " + ub.name + " are already friends."); return false; }
        insertFriend(ua, b);
        insertFriend(ub, a);
        return true;
    }

    // ---------- 2. remove connection
    public boolean removeFriendship(int a, int b) {
        User ua = findById(a), ub = findById(b);
        if (ua == null || ub == null) { System.out.println("  [!] Both users must exist (" + a + ", " + b + ")."); return false; }
        if (!deleteFriend(ua, b)) { System.out.println("  [!] " + ua.name + " and " + ub.name + " are not friends."); return false; }
        deleteFriend(ub, a);
        return true;
    }

    // ---------- 3. mutual friends
    public List<User> mutualFriends(int a, int b) {
        List<User> res = new ArrayList<>();
        User ua = findById(a), ub = findById(b);
        if (ua == null || ub == null) { System.out.println("  [!] Both users must exist."); return res; }
        for (FriendNode f = ua.friends; f != null; f = f.next)
            if (hasFriend(ub, f.friendId)) res.add(findById(f.friendId));
        return res;
    }

    // ---------- 4. display friends of a user
    public void displayFriends(int id) {
        User u = findById(id);
        if (u == null) { System.out.println("  [!] User " + id + " not found."); return; }
        System.out.print("  Friends of " + u.name + ": ");
        if (u.friends == null) { System.out.println("(none)"); return; }
        StringBuilder sb = new StringBuilder();
        for (FriendNode f = u.friends; f != null; f = f.next)
            sb.append(findById(f.friendId).name).append("(").append(f.friendId).append(f.next != null ? "), " : ")");
        System.out.println(sb);
    }

    // ---------- 5. search
    public User searchById(int id) { return findById(id); }

    public User searchByName(String name) {
        for (User u = head; u != null; u = u.next) if (u.name.equalsIgnoreCase(name)) return u;
        return null;
    }

    // ---------- 6. count
    public void countFriendsOfAll() {
        for (User u = head; u != null; u = u.next) System.out.println("  " + u.name + " -> " + u.friendCount + " friend(s)");
    }

    public static void main(String[] args) {
        FriendConnections fc = new FriendConnections();
        fc.addUser(1, "Aarav", 20); fc.addUser(2, "Diya", 21); fc.addUser(3, "Rohan", 19);
        fc.addUser(4, "Meera", 22); fc.addUser(5, "Kabir", 20); fc.addUser(2, "Dup", 1);

        System.out.println("== Add friendships ==");
        fc.addFriendship(1, 2); fc.addFriendship(1, 3); fc.addFriendship(1, 4);
        fc.addFriendship(2, 3); fc.addFriendship(2, 4); fc.addFriendship(4, 5);
        fc.addFriendship(1, 2);       // already friends
        fc.addFriendship(3, 3);       // self
        fc.addFriendship(1, 99);      // missing user
        for (int id = 1; id <= 5; id++) fc.displayFriends(id);

        System.out.println("\n== Mutual friends ==");
        System.out.println("  Aarav & Diya  : " + names(fc.mutualFriends(1, 2)));
        System.out.println("  Rohan & Meera : " + names(fc.mutualFriends(3, 4)));
        System.out.println("  Aarav & Kabir : " + names(fc.mutualFriends(1, 5)));

        System.out.println("\n== Search ==");
        System.out.println("  By name 'meera': " + fc.searchByName("meera"));
        System.out.println("  By ID 5        : " + fc.searchById(5));
        System.out.println("  By ID 42       : " + fc.searchById(42));

        System.out.println("\n== Friend counts ==");
        fc.countFriendsOfAll();

        System.out.println("\n== Remove Aarav-Diya and Meera-Kabir (and a non-friendship) ==");
        fc.removeFriendship(1, 2); fc.removeFriendship(4, 5); fc.removeFriendship(1, 5);
        for (int id = 1; id <= 5; id++) fc.displayFriends(id);
        System.out.println("  Aarav & Diya mutual now: " + names(fc.mutualFriends(1, 2)));
        fc.countFriendsOfAll();
    }

    private static String names(List<User> l) {
        if (l.isEmpty()) return "(none)";
        StringBuilder sb = new StringBuilder();
        for (User u : l) sb.append(sb.length() > 0 ? ", " : "").append(u.name);
        return sb.toString();
    }
}
