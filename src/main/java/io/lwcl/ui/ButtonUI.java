package io.lwcl.ui;

import lombok.Getter;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import java.awt.Cursor;
import java.awt.Font;
import javax.swing.*;

public class ButtonUI {

    private static final String DOUBLE_OR_NUMBER_REGEX = "(-?\\d+[.]\\d*)|(\\d+)|(-\\d+)";
    public static final String OPT_DECIMAL_REGEX = "-?\\d+[.]0*";
    public static final String ZERO_REGEX = "0*";

    private final int buttonWidth;
    private final int buttonHeight;
    private final String fontName;

    @Getter
    private JButton btnClear;
    @Getter
    private JButton btnBack;

    @Getter
    private JButton btnAdd;
    @Getter
    private JButton btnSub;
    @Getter
    private JButton btnMul;
    @Getter
    private JButton btnDiv;
    @Getter
    private JButton btnMod;

    @Getter
    private JButton btnRoot;
    @Getter
    private JButton btnPower;
    @Getter
    private JButton btnLog;

    @Getter
    private JButton btnEqual;
    @Getter
    private JButton btnPoint;

    @Getter
    private JButton btn0;
    @Getter
    private JButton btn1;
    @Getter
    private JButton btn2;
    @Getter
    private JButton btn3;
    @Getter
    private JButton btn4;
    @Getter
    private JButton btn5;
    @Getter
    private JButton btn6;
    @Getter
    private JButton btn7;
    @Getter
    private JButton btn8;
    @Getter
    private JButton btn9;

    private final Map<JButton, ButtonType> buttonTypes = new HashMap<>();

    private char selectedOperator = ' ';
    private boolean go = true; // For calculate with Opt != (=)
    private boolean addToDisplay = true; // Connect numbers in display
    private double typedValue = 0;

    public ButtonUI(int buttonWidth, int buttonHeight, String fontName) {
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
        this.fontName = fontName;
    }

    private JButton createButton(String label, int x, int y, ButtonType type) {
        JButton btn = new JButton(label);
        btn.setBounds(x, y, buttonWidth, buttonHeight);
        btn.setFont(new Font(fontName, Font.PLAIN, 28));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusable(false);
        CalculatorUI.getWindow().add(btn);

        buttonTypes.put(btn, type);

        return btn;
    }

    private double calculate(double firstNumber, double secondNumber, char operator) {
        return switch (operator) {
            case '+' -> firstNumber + secondNumber;
            case '-' -> firstNumber - secondNumber;
            case '*' -> firstNumber * secondNumber;
            case '/' -> firstNumber / secondNumber;
            case '%' -> firstNumber % secondNumber;
            case '^' -> Math.pow(firstNumber, secondNumber);
            default -> secondNumber;
        };
    }

    public List<JButton> getAllButtons() {
        List<JButton> buttons = new ArrayList<>();

        buttons.add(btnClear);
        buttons.add(btnBack);
        buttons.add(btnAdd);
        buttons.add(btnSub);
        buttons.add(btnMul);
        buttons.add(btnDiv);
        buttons.add(btnMod);

        buttons.add(btnRoot);
        buttons.add(btnPower);
        buttons.add(btnLog);

        buttons.add(btnEqual);
        buttons.add(btnPoint);

        buttons.add(btn0);
        buttons.add(btn1);
        buttons.add(btn2);
        buttons.add(btn3);
        buttons.add(btn4);
        buttons.add(btn5);
        buttons.add(btn6);
        buttons.add(btn7);
        buttons.add(btn8);
        buttons.add(btn9);

        return buttons;
    }

    public ButtonType getButtonType(JButton button) {
        return buttonTypes.get(button);
    }

    public void initButtons(int[] columns, int[] rows) {
        btnClear = createButton("C", columns[0], rows[1], ButtonType.OPERATOR);
        btnClear.addActionListener(this::onClearButtonClick);

        btnBack = createButton("<-", columns[1], rows[1], ButtonType.OPERATOR);
        btnBack.addActionListener(this::onBackButtonClick);

        btnAdd = createButton("+", columns[3], rows[4], ButtonType.OPERATOR);
        btnAdd.addActionListener(this::onAdditionButtonClick);

        btnSub = createButton("-", columns[3], rows[3], ButtonType.OPERATOR);
        btnSub.addActionListener(this::onSubtractionButtonClick);

        btnMul = createButton("*", columns[3], rows[2], ButtonType.OPERATOR);
        btnMul.addActionListener(this::onMultiplyButtonClick);

        btnDiv = createButton("/", columns[3], rows[1], ButtonType.OPERATOR);
        btnDiv.addActionListener(this::onDivideButtonClick);

        btnMod = createButton("%", columns[2], rows[1], ButtonType.OPERATOR);
        btnMod.addActionListener(this::onModulusButtonClick);

        btnRoot = createButton("√", columns[4], rows[1], ButtonType.OPERATOR);
        btnRoot.addActionListener(this::onRootButtonClick);
        btnRoot.setVisible(false);

        btnPower = createButton("pow", columns[4], rows[2], ButtonType.OPERATOR);
        btnPower.addActionListener(this::onPowerButtonClick);
        btnPower.setFont(new Font(fontName, Font.PLAIN, 24));
        btnPower.setVisible(false);

        btnLog = createButton("ln", columns[4], rows[3], ButtonType.OPERATOR);
        btnLog.addActionListener(this::onLogButtonClick);
        btnLog.setVisible(false);

        btnEqual = createButton("=", columns[2], rows[5], ButtonType.EQUAL);
        btnEqual.addActionListener(this::onEqualButtonClick);
        btnEqual.setSize(2 * buttonWidth + 10, buttonHeight);

        btnPoint = createButton(".", columns[0], rows[5], ButtonType.NUMBER);
        btnPoint.addActionListener(this::onPointButtonClick);

        btn0 = createButton("0", columns[1], rows[5], ButtonType.NUMBER);
        btn0.addActionListener(e -> handleNumberButtonClick("0"));
        btn1 = createButton("1", columns[0], rows[4], ButtonType.NUMBER);
        btn1.addActionListener(e -> handleNumberButtonClick("1"));
        btn2 = createButton("2", columns[1], rows[4], ButtonType.NUMBER);
        btn2.addActionListener(e -> handleNumberButtonClick("2"));
        btn3 = createButton("3", columns[2], rows[4], ButtonType.NUMBER);
        btn3.addActionListener(e -> handleNumberButtonClick("3"));
        btn4 = createButton("4", columns[0], rows[3], ButtonType.NUMBER);
        btn4.addActionListener(e -> handleNumberButtonClick("4"));
        btn5 = createButton("5", columns[1], rows[3], ButtonType.NUMBER);
        btn5.addActionListener(e -> handleNumberButtonClick("5"));
        btn6 = createButton("6", columns[2], rows[3], ButtonType.NUMBER);
        btn6.addActionListener(e -> handleNumberButtonClick("6"));
        btn7 = createButton("7", columns[0], rows[2], ButtonType.NUMBER);
        btn7.addActionListener(e -> handleNumberButtonClick("7"));
        btn8 = createButton("8", columns[1], rows[2], ButtonType.NUMBER);
        btn8.addActionListener(e -> handleNumberButtonClick("8"));
        btn9 = createButton("9", columns[2], rows[2], ButtonType.NUMBER);
        btn9.addActionListener(e -> handleNumberButtonClick("9"));
    }

    private void onBackButtonClick(ActionEvent event) {
        String str = CalculatorUI.getInputScreen().getText();
        StringBuilder str2 = new StringBuilder();
        for (int i = 0; i < (str.length() - 1); i++) {
            str2.append(str.charAt(i));
        }
        if (str2.toString().isEmpty()) {
            CalculatorUI.getInputScreen().setText("0");
        } else {
            CalculatorUI.getInputScreen().setText(str2.toString());
        }
    }

    private void onClearButtonClick(ActionEvent event) {
        CalculatorUI.getInputScreen().setText("0");
        selectedOperator = ' ';
        typedValue = 0;
    }

    private void onPointButtonClick(ActionEvent event) {
        if (addToDisplay) {
            if (!CalculatorUI.getInputScreen().getText().contains(".")) {
                CalculatorUI.getInputScreen().setText(CalculatorUI.getInputScreen().getText() + ".");
            }
        } else {
            CalculatorUI.getInputScreen().setText("0.");
            addToDisplay = true;
        }
        go = true;
    }

    private void onEqualButtonClick(ActionEvent event) {
        if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, CalculatorUI.getInputScreen().getText()))
            return;

        if (go) {
            typedValue = calculate(typedValue, Double.parseDouble(CalculatorUI.getInputScreen().getText()), selectedOperator);
            if (Pattern.matches(OPT_DECIMAL_REGEX, String.valueOf(typedValue))) {
                CalculatorUI.getInputScreen().setText(String.valueOf((int) typedValue));
            } else {
                CalculatorUI.getInputScreen().setText(String.valueOf(typedValue));
            }
            selectedOperator = '=';
            addToDisplay = false;
        }
    }

    private void onAdditionButtonClick(ActionEvent event) {
        if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, CalculatorUI.getInputScreen().getText()))
            return;

        if (go) {
            typedValue = calculate(typedValue, Double.parseDouble(CalculatorUI.getInputScreen().getText()), selectedOperator);
            if (Pattern.matches(OPT_DECIMAL_REGEX, String.valueOf(typedValue))) {
                CalculatorUI.getInputScreen().setText(String.valueOf((int) typedValue));
            } else {
                CalculatorUI.getInputScreen().setText(String.valueOf(typedValue));
            }
            selectedOperator = '+';
            go = false;
            addToDisplay = false;
        } else {
            selectedOperator = '+';
        }
    }

    private void onSubtractionButtonClick(ActionEvent event) {
        if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, CalculatorUI.getInputScreen().getText()))
            return;

        if (go) {
            typedValue = calculate(typedValue, Double.parseDouble(CalculatorUI.getInputScreen().getText()), selectedOperator);
            if (Pattern.matches(OPT_DECIMAL_REGEX, String.valueOf(typedValue))) {
                CalculatorUI.getInputScreen().setText(String.valueOf((int) typedValue));
            } else {
                CalculatorUI.getInputScreen().setText(String.valueOf(typedValue));
            }

            selectedOperator = '-';
            go = false;
            addToDisplay = false;
        } else {
            selectedOperator = '-';
        }
    }

    private void onMultiplyButtonClick(ActionEvent event) {
        if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, CalculatorUI.getInputScreen().getText()))
            return;

        if (go) {
            typedValue = calculate(typedValue, Double.parseDouble(CalculatorUI.getInputScreen().getText()), selectedOperator);
            if (Pattern.matches(OPT_DECIMAL_REGEX, String.valueOf(typedValue))) {
                CalculatorUI.getInputScreen().setText(String.valueOf((int) typedValue));
            } else {
                CalculatorUI.getInputScreen().setText(String.valueOf(typedValue));
            }
            selectedOperator = '*';
            go = false;
            addToDisplay = false;
        } else {
            selectedOperator = '*';
        }
    }

    private void onDivideButtonClick(ActionEvent event) {
        if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, CalculatorUI.getInputScreen().getText()))
            return;

        if (go) {
            typedValue = calculate(typedValue, Double.parseDouble(CalculatorUI.getInputScreen().getText()), selectedOperator);
            if (Pattern.matches(OPT_DECIMAL_REGEX, String.valueOf(typedValue))) {
                CalculatorUI.getInputScreen().setText(String.valueOf((int) typedValue));
            } else {
                CalculatorUI.getInputScreen().setText(String.valueOf(typedValue));
            }
            selectedOperator = '/';
            go = false;
            addToDisplay = false;
        } else {
            selectedOperator = '/';
        }
    }

    private void onModulusButtonClick(ActionEvent event) {
        if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, CalculatorUI.getInputScreen().getText()) || !go)
            return;

        typedValue = calculate(typedValue, Double.parseDouble(CalculatorUI.getInputScreen().getText()), selectedOperator);
        if (Pattern.matches(OPT_DECIMAL_REGEX, String.valueOf(typedValue))) {
            CalculatorUI.getInputScreen().setText(String.valueOf((int) typedValue));
        } else {
            CalculatorUI.getInputScreen().setText(String.valueOf(typedValue));
        }
        selectedOperator = '%';
        go = false;
        addToDisplay = false;
    }
    private void onRootButtonClick(ActionEvent event) {
        if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, CalculatorUI.getInputScreen().getText()))
            return;

        if (go) {
            typedValue = Math.sqrt(Double.parseDouble(CalculatorUI.getInputScreen().getText()));
            if (Pattern.matches(OPT_DECIMAL_REGEX, String.valueOf(typedValue))) {
                CalculatorUI.getInputScreen().setText(String.valueOf((int) typedValue));
            } else {
                CalculatorUI.getInputScreen().setText(String.valueOf(typedValue));
            }
            selectedOperator = '√';
            addToDisplay = false;
        }
    }

    private void onPowerButtonClick(ActionEvent event) {
        if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, CalculatorUI.getInputScreen().getText()))
            return;

        if (go) {
            typedValue = calculate(typedValue, Double.parseDouble(CalculatorUI.getInputScreen().getText()), selectedOperator);
            if (Pattern.matches(OPT_DECIMAL_REGEX, String.valueOf(typedValue))) {
                CalculatorUI.getInputScreen().setText(String.valueOf((int) typedValue));
            } else {
                CalculatorUI.getInputScreen().setText(String.valueOf(typedValue));
            }
            selectedOperator = '^';
            go = false;
            addToDisplay = false;
        } else {
            selectedOperator = '^';
        }
    }

    private void onLogButtonClick(ActionEvent event) {
        if (!Pattern.matches(DOUBLE_OR_NUMBER_REGEX, CalculatorUI.getInputScreen().getText()))
            return;

        if (go) {
            typedValue = Math.log(Double.parseDouble(CalculatorUI.getInputScreen().getText()));
            if (Pattern.matches(OPT_DECIMAL_REGEX, String.valueOf(typedValue))) {
                CalculatorUI.getInputScreen().setText(String.valueOf((int) typedValue));
            } else {
                CalculatorUI.getInputScreen().setText(String.valueOf(typedValue));
            }
            selectedOperator = 'l';
            addToDisplay = false;
        }
    }

    private void handleNumberButtonClick(String digit) {
        if (addToDisplay) {
            if (Pattern.matches(ZERO_REGEX, CalculatorUI.getInputScreen().getText())) {
                CalculatorUI.getInputScreen().setText(digit);
            } else {
                CalculatorUI.getInputScreen().setText(CalculatorUI.getInputScreen().getText() + digit);
            }
        } else {
            CalculatorUI.getInputScreen().setText(digit);
            addToDisplay = true;
        }
        go = true;
    }
}
