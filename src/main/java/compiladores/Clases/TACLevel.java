package app.models;

import java.util.ArrayList;
import java.util.List;

public class TACLevel {

    List<String> signs;
    List<String> factors;

    public TACLevel() {
        signs = new ArrayList<>();
        factors = new ArrayList<>();
    }

    public List<String> getSigns() {
        return signs;
    }

    public List<String> getFactors() {
        return factors;
    }

}
