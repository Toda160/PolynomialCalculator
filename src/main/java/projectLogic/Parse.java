package projectLogic;

import java.util.TreeMap;

public class Parse {
    public TreeMap<Integer, Double> parsePolynomial(String input) {
        input = input.replaceAll("-", "+-");
        TreeMap<Integer, Double> terms = new TreeMap<>();
        String[] termTokens = input.split("\\+");
        for (String termToken : termTokens) {
            if (!termToken.isEmpty()) {
                addTerm(terms, termToken);
            }
        }
        return terms;
    }

    private void addTerm(TreeMap<Integer, Double> terms, String term) {
        double coeficient;
        int exponent;
        if (term.contains("x")) {
            String[] parts = term.split("x");
            if (parts.length > 0 && !parts[0].isEmpty()) {
                coeficient = Double.parseDouble(parts[0]);
            } else {
                coeficient = 1;
            }
            if (parts.length > 1) {
                String exponentPart = parts[1].replace("^", "");
                if (!exponentPart.isEmpty()) {
                    exponent = Integer.parseInt(exponentPart);
                } else {
                    exponent = 1;
                }
            } else {
                exponent = 1;
            }
        } else {
            coeficient = Double.parseDouble(term);
            exponent = 0;
        }

        terms.put(exponent, coeficient);
    }
}
