package src.assembler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Assembler {
    public static void main(String[] args) {
        String inputFileName = args[0];
        String outputFileName = inputFileName.split("\\.")[0] + ".hack";

        try {
            File inputFile = new File(inputFileName);
            Parser parser = new Parser(inputFile);
            Code code = new Code();
            SymbolTable symbolTable = new SymbolTable();
            FileWriter writer = new FileWriter(outputFileName);

            firstPass(parser, symbolTable);
            parser.reset(inputFile);

            int variableAddress = 16;

            while (parser.hasMoreLines()) {
                parser.advance();

                if (parser.commandType().equals("A_COMMAND")) {
                    variableAddress = writeAInstruction(parser, writer, symbolTable, variableAddress);
                } else if (parser.commandType().equals("C_COMMAND")) {
                    writeCInstruction(parser, code, writer);
                }
            }
            
            writer.close();
            System.out.println("Assembly completed.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void firstPass(Parser parser, SymbolTable symbolTable) {
        int countInstruction = 0;

        while (parser.hasMoreLines()) {
            parser.advance();
            String commandType = parser.commandType();

            if (commandType.equals("A_COMMAND") || commandType.equals("C_COMMAND")) {
                countInstruction++;
            } else if (commandType.equals("L_COMMAND")) {
                symbolTable.addEntry(parser.symbol(), countInstruction);
            }
        }
    }

    private static int writeAInstruction(Parser parser, FileWriter writer, SymbolTable symbolTable, int variableAddress) throws IOException {
        String symbol = parser.symbol();
        if (Character.isDigit(symbol.charAt(0))) {
            writer.write(decimalStringTo16BitBinaryString(symbol));
            writer.write('\n');
            return variableAddress;
        } else {
            if (symbolTable.contains(symbol)) {
                writer.write(decimalStringTo16BitBinaryString(String.valueOf(symbolTable.getAddress(symbol))));
                writer.write('\n');
                return variableAddress;
            } else {
                symbolTable.addEntry(symbol, variableAddress);
                writer.write(decimalStringTo16BitBinaryString(String.valueOf(variableAddress)));
                writer.write('\n');
                return variableAddress + 1;
            }
        }
    }

    private static String decimalStringTo16BitBinaryString(String decimalString) {
        int decimal = Integer.parseInt(decimalString);
        String binaryString = Integer.toBinaryString(decimal);
        return String.format("%16s", binaryString).replace(' ', '0');
    }

    private static void writeCInstruction(Parser parser, Code code, FileWriter writer) throws IOException {
        writer.write("111" + code.comp(parser.comp()) + code.dest(parser.dest()) + code.jump(parser.jump()));
        writer.write('\n');
    }
}
