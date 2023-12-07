package io.lwcl;

import io.lwcl.ui.CalculatorUI;

import java.util.logging.Logger;

public class App {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger("Calculator");

        new CalculatorUI(logger);
    }
}