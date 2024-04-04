package nl.theredhead.donderdagseweek.logic;

public class Sanity {
    static public void Enforce(Boolean predicate, String message) throws Exception {
        if (! predicate) {
            throw new Exception(message);
        }
    }
}
