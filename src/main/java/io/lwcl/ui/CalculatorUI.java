package io.lwcl.ui;

import io.lwcl.theme.ThemeLoader;
import io.lwcl.theme.properties.Theme;
import lombok.Getter;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.util.Map;
import java.awt.Color;
import java.util.logging.Logger;
import javax.swing.*;

import static io.lwcl.utils.ColorUtil.hex2Color;

public class CalculatorUI {

    private static final String FONT_NAME = "Segoe";
    private static final String APPLICATION_TITLE = "Calculator";
    private static final int WINDOW_WIDTH = 410;
    private static final int WINDOW_HEIGHT = 600;
    private static final int BUTTON_WIDTH = 80;
    private static final int BUTTON_HEIGHT = 70;
    private static final int MARGIN_X = 20;
    private static final int MARGIN_Y = 60;


    private final ButtonUI buttonUI;

    @Getter
    private static final JFrame window = new JFrame(APPLICATION_TITLE);
    @Getter
    private static JTextField inputScreen;
    private final Logger logger;

    private JComboBox<String> comboTheme;
    private JComboBox<String> comboCalculatorType;

    private final Map<String, Theme> themesMap;

    public CalculatorUI(Logger logger) {
        this.logger = logger;

        themesMap = ThemeLoader.loadThemes();
        buttonUI = new ButtonUI(BUTTON_WIDTH, BUTTON_HEIGHT, FONT_NAME);

        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setLocationRelativeTo(null);

        int[] columns = {MARGIN_X, MARGIN_X + 90, MARGIN_X + 90 * 2, MARGIN_X + 90 * 3, MARGIN_X + 90 * 4};
        int[] rows = {MARGIN_Y, MARGIN_Y + 100, MARGIN_Y + 100 + 80, MARGIN_Y + 100 + 80 * 2, MARGIN_Y + 100 + 80 * 3, MARGIN_Y + 100 + 80 * 4};

        initInputScreen(columns, rows);
        buttonUI.initButtons(columns, rows);
        initCalculatorTypeSelector();

        initThemeSelector();

        window.setLayout(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    private static void initInputScreen(int[] columns, int[] rows) {
        inputScreen = new JTextField("0");
        inputScreen.setBounds(columns[0], rows[0], 350, 70);
        inputScreen.setEditable(false);
        inputScreen.setBackground(Color.WHITE);
        inputScreen.setFont(new Font(FONT_NAME, Font.PLAIN, 33));
        window.add(inputScreen);
    }

    private void initThemeSelector() {
        comboTheme = createComboBox(themesMap.keySet().toArray(new String[0]), 230, 30, "Theme");
        comboTheme.addItemListener(event -> {
            if (event.getStateChange() != ItemEvent.SELECTED)
                return;

            String selectedTheme = (String) event.getItem();
            applyTheme(themesMap.get(selectedTheme));
        });

        if (themesMap.entrySet().iterator().hasNext()) {
            applyTheme(themesMap.entrySet().iterator().next().getValue());
        }
    }

    private void initCalculatorTypeSelector() {
        comboCalculatorType = createComboBox(new String[]{"Standard", "Scientific"}, 20, 30, "Calculator type");
        comboCalculatorType.addItemListener(event -> {
            if (event.getStateChange() != ItemEvent.SELECTED)
                return;

            String selectedItem = (String) event.getItem();
            switch (selectedItem) {
                case "Standard":
                    window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
                    buttonUI.getBtnRoot().setVisible(false);
                    buttonUI.getBtnPower().setVisible(false);
                    buttonUI.getBtnLog().setVisible(false);
                    break;
                case "Scientific":
                    window.setSize(WINDOW_WIDTH + 80, WINDOW_HEIGHT);
                    buttonUI.getBtnRoot().setVisible(true);
                    buttonUI.getBtnPower().setVisible(true);
                    buttonUI.getBtnLog().setVisible(true);
                    break;
                default:
                    logger.info("Unknown calculator type: " + selectedItem);
                    break;
            }
        });
    }

    private JComboBox<String> createComboBox(String[] items, int x, int y, String toolTip) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setBounds(x, y, 140, 25);
        combo.setToolTipText(toolTip);
        combo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        window.add(combo);

        return combo;
    }

    private void applyTheme(Theme theme) {
        // Set background color
        window.getContentPane().setBackground(hex2Color(theme.getApplicationBackground()));
        inputScreen.setBackground(hex2Color(theme.getApplicationBackground()));
        comboCalculatorType.setBackground(hex2Color(theme.getApplicationBackground()));
        comboTheme.setBackground(hex2Color(theme.getApplicationBackground()));

        // Set foreground color for text elements
        comboCalculatorType.setForeground(hex2Color(theme.getTextColor()));
        comboTheme.setForeground(hex2Color(theme.getTextColor()));
        inputScreen.setForeground(hex2Color(theme.getTextColor()));

        for (JButton button : buttonUI.getAllButtons()) {
            switch (buttonUI.getButtonType(button)) {
                case NUMBER:
                    button.setForeground(hex2Color(theme.getTextColor()));
                    button.setBackground(hex2Color(theme.getNumbersBackground()));
                    break;
                case OPERATOR:
                    button.setForeground(hex2Color(theme.getTextColor()));
                    button.setBackground(hex2Color(theme.getOperatorBackground()));
                    break;
                case EQUAL:
                    button.setForeground(hex2Color(theme.getBtnEqualTextColor()));
                    button.setBackground(hex2Color(theme.getBtnEqualBackground()));
                    break;
            }
        }
    }
}
