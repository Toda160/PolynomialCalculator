package data;
import java.util.Map;
import java.util.TreeMap;

public class PolynomialOperations {
    public static boolean isIntegrate = false;
    public static Polynomial add(Polynomial p1, Polynomial p2) {
        TreeMap<Integer, Double> resultTerms = new TreeMap<>();

        for (Map.Entry<Integer, Double> entry : p1.getTerms().entrySet()) {
            resultTerms.put(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<Integer, Double> entry : p2.getTerms().entrySet()) {
            int key = entry.getKey();
            double aux = resultTerms.getOrDefault(key, 0.0) + entry.getValue();//daca e 0, sa nu il puna in map
            if (aux != 0) {
                resultTerms.put(key, aux);
            } else {
                resultTerms.remove(key);
            }
        }
        isIntegrate=false;
        return new Polynomial(resultTerms);
    }

    public static Polynomial subtract(Polynomial p1, Polynomial p2) {
        TreeMap<Integer, Double> resultTerms = new TreeMap<>();

        for (Map.Entry<Integer, Double> entry : p1.getTerms().entrySet()) {
            resultTerms.put(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<Integer, Double> entry : p2.getTerms().entrySet()) {
            int key = entry.getKey();
            double aux = resultTerms.getOrDefault(key, 0.0) - entry.getValue();//daca e 0, sa nu il puna in map
            if (aux != 0) {
                resultTerms.put(key, aux);
            } else {
                resultTerms.remove(key);
            }
        }

        isIntegrate=false;
        return new Polynomial(resultTerms);
    }

    public static Polynomial multiply(Polynomial p1, Polynomial p2) {
        TreeMap<Integer, Double> resultTerms = new TreeMap<>();
        for (Map.Entry<Integer, Double> entry : p1.getTerms().entrySet()) {
            int exponent1 = entry.getKey();
            double coeficient1 = entry.getValue();

            for (Map.Entry<Integer, Double> entry1 : p2.getTerms().entrySet()) {
                int exponent2 = entry1.getKey();
                double coeficient2 = entry1.getValue();

                double coeficient = coeficient1 * coeficient2;
                int exponent = exponent1 + exponent2;

                double coeficientaux = resultTerms.getOrDefault(exponent, 0.0);
                resultTerms.put(exponent, coeficientaux + coeficient);
            }
        }
        isIntegrate=false;
        return new Polynomial(resultTerms);
    }


    public static Polynomial derive(Polynomial p) {
        TreeMap<Integer, Double> resultTerms = new TreeMap<>();

        for (Map.Entry<Integer, Double> entry : p.getTerms().entrySet()) {
            int exponent = entry.getKey(); //exponent
            double coeficient = entry.getValue();//coeficient
            if (exponent == 1)
            {
                resultTerms.put(0, coeficient);
            }
            else if (exponent>1){
                resultTerms.put(exponent-1, exponent*coeficient);
            }
        }
        isIntegrate=false;
        return new Polynomial(resultTerms);
    }

    public static Polynomial integrate(Polynomial p) {
        TreeMap<Integer, Double> resultTerms = new TreeMap<>();
        for (Map.Entry<Integer, Double> entry : p.getTerms().entrySet()) {
            double coeficient = entry.getValue();
            int exponent = entry.getKey();
            resultTerms.put(exponent + 1,  coeficient / (exponent + 1));
        }
        isIntegrate=true;
        return new Polynomial(resultTerms);
    }
    public static Polynomial divide(Polynomial dividend, Polynomial divisor) {
        if (divisor.getDegree() == 0 && divisor.getTerms().get(0) == 0) {
            throw new ArithmeticException("Division by zero.");
        }

        TreeMap<Integer, Double> quotientTerms = new TreeMap<>(dividend.getTerms());
        TreeMap<Integer, Double> divisorTerms = divisor.getTerms();

        while (quotientTerms.size() >= divisorTerms.size() && !isAllZeroes(quotientTerms)) {
            Map.Entry<Integer, Double> highestDegreeTerm = quotientTerms.lastEntry();
            Map.Entry<Integer, Double> highestDegreeDivisorTerm = divisorTerms.lastEntry();

            int quotientExponent = highestDegreeTerm.getKey() - highestDegreeDivisorTerm.getKey();
            double quotientCoefficient = highestDegreeTerm.getValue() / highestDegreeDivisorTerm.getValue();

            TreeMap<Integer, Double> temp = new TreeMap<>();
            for (Map.Entry<Integer, Double> entry : divisorTerms.entrySet()) {
                temp.put(entry.getKey() + quotientExponent, entry.getValue() * quotientCoefficient);
            }

            for (Map.Entry<Integer, Double> entry : temp.entrySet()) {
                int key = entry.getKey();
                double value = entry.getValue();
                double existingValue = quotientTerms.getOrDefault(key, 0.0);
                quotientTerms.put(key, existingValue - value);
            }

            if (quotientTerms.lastEntry().getValue() == 0) {
                quotientTerms.remove(quotientTerms.lastEntry().getKey());
            }
        }

        return new Polynomial(quotientTerms);
    }

    private static boolean isAllZeroes(TreeMap<Integer, Double> terms) {
        for (double coefficient : terms.values()) {
            if (coefficient != 0) {
                return false;
            }
        }
        return true;
    }
}