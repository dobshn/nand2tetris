package src.vmtranslator;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Parser {
    Scanner scanner;
    String currentCommand;

    public Parser(File inputFile) throws IOException {
        scanner = new Scanner(inputFile);
    }

    public boolean hasMoreLines() {
        return scanner.hasNextLine();
    }

    // advance
    public void advance() {
        currentCommand = null;
        while (hasMoreLines()) {
            String line = scanner.nextLine().trim();

            if (isCommandLine(line)) {
                currentCommand = extractCommand(line);
                break;
            }
        }
    }

    private boolean isCommandLine(String line) {
        return !line.startsWith("//") && !line.isEmpty();
    }

    private String extractCommand(String line) {
        return line.split("//")[0].trim();
    }

    // commandType
    public String commandType() {
        String command = currentCommand.split(" ")[0].trim();
        return switch (command) {
            case "add", "sub", "neg", "eq", "gt", "lt", "and", "or", "not" -> "C_ARITHMETIC";
            case "push" -> "C_PUSH";
            case "pop" -> "C_POP";
            case "label" -> "C_LABEL";
            case "goto" -> "C_GOTO";
            case "if-goto" -> "C_IF";
            case "function" -> "C_FUNCTION";
            case "return" -> "C_RETURN";
            case "call" -> "C_CALL";

            default -> null;
        };
    }

    // arg1
    public String arg1() {
        if (!commandType().equals("C_RETURN")) {
            if (commandType().equals("C_ARITHMETIC")) {
                return currentCommand.split(" ")[0].trim();
            } else {
                return currentCommand.split(" ")[1].trim();
            }
        }
        return null;
    }

    // arg2
    public Integer arg2() {
        if (commandType().equals("C_PUSH")
                || commandType().equals("C_POP")
                || commandType().equals("C_FUNCTION")
                || commandType().equals("C_CALL")) {
            return Integer.parseInt(currentCommand.split(" ")[2].trim());
        }
        return null;
    }

}
