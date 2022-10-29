package a0;

import java.util.HashSet;

public class Roster {
    private HashSet<String> db = new HashSet<>();
    boolean AddID(String andrewID) {
        if (ExistsID(andrewID)) return false;
        db.add(andrewID);
        return true;
    }

    boolean ExistsID(String andrewID) {
        return db.contains(andrewID);
    }

    boolean RemoveID(String andrewID) {
        if (!ExistsID(andrewID)) return false;
        db.remove(andrewID);
        return true;
    }
}
