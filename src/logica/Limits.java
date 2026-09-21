package logica;

public enum Limits {
    MAX_FDC(2.0), MIN_FDC(0.5);

    private final double value;

    Limits(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}