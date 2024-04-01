package nl.theredhead.donderdagseweek.models;

import androidx.compose.foundation.layout.AlignmentLineProvider;

import java.util.Arrays;
import java.util.List;

import nl.theredhead.donderdagseweek.Logic.Sanity;

public class DayCode {
    static List<String> PossibleValues = Arrays.asList("ma", "di", "wo", "do", "vr", "za", "zo");
    private String Value;
    public String getValue() {
        return Value;
    }
    public void setValue(String value) {
        Value = value;
    }

    protected DayCode(String code) {
        setValue(code);
    }
    public static DayCode parse(String text) throws Exception {
        Sanity.Enforce(text.length() == 2, "Text must be exactly 2 characters long");
        if (PossibleValues.contains(text)) {
            return new DayCode(text);
        }
        throw new Exception("Not a valid DayCode: " + text);
    }
}
