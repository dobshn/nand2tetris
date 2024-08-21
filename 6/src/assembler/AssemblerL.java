package src.assembler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AssemblerL {
    public static void main(String[] args) {
        String inputFileName = args[0];
        String outputFileName = inputFileName.split("\\.")[0] + ".hack";

        try {
            File inputFile = new File(inputFileName);
            Parser parser = new Parser(inputFile);
            Code code = new Code();
            FileWriter writer = new FileWriter(outputFileName);

            while (parser.hasMoreLines()) {
                parser.advance();

                if (parser.commandType().equals("A_COMMAND")) {
                    writeAInstruction(parser, writer);
                } else if (parser.commandType().equals("C_COMMAND")) {
                    writeCInstruction(parser, code, writer);
                }
            }

            writer.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void writeAInstruction(Parser parser, FileWriter writer) throws IOException {
        writer.write(decimalStringTo16BitBinaryString(parser.symbol()));
        writer.write('\n');
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