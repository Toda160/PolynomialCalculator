package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeMap;
import data.Polynomial;
import data.PolynomialOperations;
import projectLogic.Parse;

public class PolynomialCalculator extends JFrame {
    private Parse parse = new Parse();
    private JTextField poly1TextField, poly2TextField, resultTextField;
    private Polynomial poly1, poly2, result;
    private TreeMap<Integer, Double> poly1Terms;
    private TreeMap<Integer, Double> poly2Terms;

    public PolynomialCalculator() {
        setTitle("Calculator Polinoame");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel poly1Panel = new JPanel(new GridLayout(2, 1));
        poly1Panel.add(new JLabel("Polinom 1:"));
        poly1TextField = new JTextField();
        poly1Panel.add(poly1TextField);

        JPanel poly2Panel = new JPanel(new GridLayout(2, 1));
        poly2Panel.add(new JLabel("Polinom 2:"));
        poly2TextField = new JTextField();
        poly2Panel.add(poly2TextField);

        JPanel operationPanel = new JPanel(new GridLayout(3, 2));
        JButton addButton = new JButton("Adunare");
        JButton subtractButton = new JButton("Scadere");
        JButton multiplyButton = new JButton("Inmultire");
        JButton divideButton = new JButton("Impartire");
        JButton derivativeButton = new JButton("Derivare");
        JButton integralButton = new JButton("Integrare");
        operationPanel.add(addButton);
        operationPanel.add(subtractButton);
        operationPanel.add(multiplyButton);
        operationPanel.add(divideButton);
        operationPanel.add(derivativeButton);
        operationPanel.add(integralButton);

        JPanel resultPanel = new JPanel(new GridLayout(2, 1));
        resultPanel.add(new JLabel("Rezultat:"));
        resultTextField = new JTextField();
        resultPanel.add(resultTextField);

        setLayout(new GridLayout(2, 2));
        add(poly1Panel);
        add(poly2Panel);
        add(resultPanel);
        add(operationPanel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performOperation(PolynomialOperations::add);
            }
        });

        subtractButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performOperation(PolynomialOperations::subtract);
            }
        });

        multiplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performOperation(PolynomialOperations::multiply);
            }
        });

        divideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performOperation(PolynomialOperations::divide);
            }
        });


        derivativeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSingleOperation(PolynomialOperations::derive);
            }
        });

        integralButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSingleOperation(PolynomialOperations::integrate);
            }
        });
    }

    private void performOperation(Operation operation) {
        poly1Terms = parse.parsePolynomial(poly1TextField.getText());
        poly2Terms = parse.parsePolynomial(poly2TextField.getText());
        poly1 = new Polynomial(poly1Terms);
        poly2 = new Polynomial(poly2Terms);
        result = operation.apply(poly1, poly2);
        resultTextField.setText(result.displayPolynomial());
    }

    private void performSingleOperation(SingleOperation singleOperation) {
        poly1Terms = parse.parsePolynomial(poly1TextField.getText());
        poly1 = new Polynomial(poly1Terms);
        result = singleOperation.apply(poly1);
        if (PolynomialOperations.isIntegrate)
            resultTextField.setText(result.displayPolynomial()+" + C");
        else resultTextField.setText(result.displayPolynomial());
    }


    interface Operation {
        Polynomial apply(Polynomial p1, Polynomial p2);
    }

    interface SingleOperation {
        Polynomial apply(Polynomial p);
    }
}
