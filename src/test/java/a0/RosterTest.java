package a0;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RosterTest {
    @Test
    public void test() {
        Roster roster = new Roster();
        // add ID
        assertTrue(roster.AddID("people1"));
        assertTrue(roster.AddID("people2"));
        assertFalse(roster.AddID("people1"));
        // check exist
        assertTrue(roster.ExistsID("people2"));
        assertFalse(roster.ExistsID("people3"));
        // remove
        assertTrue(roster.RemoveID("people1"));
        assertFalse(roster.RemoveID("people1"));
    }
}
