package model;

public class Equation {
    private String equation;
    private double root;

    public Equation(String equation, double root) {
        this.equation = equation;
        this.root = root;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public double getRoot() {
        return root;
    }

    public void setRoot(double root) {
        this.root = root;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equation)) return false;

        Equation equation1 = (Equation) o;

        if (Double.compare(getRoot(), equation1.getRoot()) != 0) return false;
        return getEquation().equals(equation1.getEquation());
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getEquation().hashCode();
        temp = Double.doubleToLongBits(getRoot());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return equation + " = " + root;
    }

}
