package data;
import java.util.TreeMap;
import java.util.NavigableMap;
import java.util.Map;

public class Polynomial {
    private TreeMap<Integer, Double> terms;  // TreeMap pentru a stoca termenii (puterea -> coeficient)

    public Polynomial(TreeMap<Integer, Double> terms) {
        this.terms = terms;
    }

    public TreeMap<Integer, Double> getTerms() {
        return terms;
    }
    public int getDegree() {
        if (terms.isEmpty()) {
            return 0; // grad 0 daca nu are termeni
        }
        else {
            return terms.lastKey();
        }
    }
    public String displayPolynomial() {
        StringBuilder result = new StringBuilder();
        NavigableMap<Integer, Double> reversedTerms = terms.descendingMap();
        for (Map.Entry<Integer, Double> entry : reversedTerms.entrySet()) {
            double coefficient = entry.getValue();
            int exponent = entry.getKey();

            if ((!result.isEmpty()) && coefficient > 0) {
                result.append("+");
            }

            if (coefficient < 0) {
                result.append(coefficient);
            } else {
                result.append(Math.abs(coefficient));
            }

            if (exponent > 0) {
                result.append("x");
                if (exponent > 1) {
                    result.append("^").append(exponent);
                }
            }
        }

        // În cazul în care polinomul este gol, returnăm "0"
        if (result.isEmpty()) {
            return "0";
        } else {
            return result.toString().trim();
        }
    }
}