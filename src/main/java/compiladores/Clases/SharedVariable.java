package compiladores.Clases;

import java.util.Objects;

public class SharedVariable {
    String original;
    String shared;

    public SharedVariable(String original) {
        this.original = original;
    }

    public SharedVariable(String original, String shared) {
        this.original = original;
        this.shared = shared;
    }

    public String getOriginal() {
        return original;
    }    

    public String getShared() {
        return shared;
    }    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SharedVariable that = (SharedVariable) o;
        return original.equals(that.original);
    }

    @Override
    public int hashCode() {
        return Objects.hash(original);
    }

    @Override
    public String toString() {
        return "SharedVariable{" +
                "original='" + original + '\'' +
                ", shared='" + shared + '\'' +
                '}';
    }
}