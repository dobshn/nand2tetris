import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
    private Scanner scanner;
    private String currentCommand;

    public Parser (File inputFile) throws FileNotFoundException {
        scanner = new Scanner(inputFile);
    }

    public void reset(File inputFile) throws FileNotFoundException {
        scanner.close();
        this.scanner = new Scanner(inputFile);
    }

    public boolean hasMoreLines () {
        return scanner.hasNextLine();
    }

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

    public String commandType() {
        if (currentCommand == null) return null;
        if (currentCommand.startsWith("@")) return "A_COMMAND";
        if (currentCommand.startsWith("(")) return "L_COMMAND";
        return "C_COMMAND";
    }

    public String symbol() {
        if (commandType().equals("A_COMMAND")) {
            return currentCommand.split("@")[1];
        }
        if (commandType().equals("L_COMMAND")) {
            return currentCommand.split("\\(")[1].split("\\)")[0];
        }
        return null;
    }

    public String dest() {
        if (commandType().equals("C_COMMAND") && currentCommand.contains("=")) {
            return currentCommand.split("=")[0];
        }
        return null;
    }

    public String comp() {
        if (commandType().equals("C_COMMAND")) {
            String[] parts = currentCommand.split(";");
            String[] compParts = parts[0].split("=");
            return compParts[compParts.length - 1];
        }
        return null;
    }

    public String jump() {
        if (commandType().equals("C_COMMAND") && currentCommand.contains(";")) {
            return currentCommand.split(";")[1];
        }
        return null;
    }
}
