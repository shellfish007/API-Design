package a0;

import java.util.HashSet;
import java.util.regex.Pattern;

public class Roster {
    private HashSet<String> db = new HashSet<>();
    boolean AddID(String andrewID) {
        if (!Pattern.matches("[a-z]+[0-9]*", andrewID)) return false;
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
