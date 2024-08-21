package src.vmtranslator;

import src.assembler.Parser;

import java.io.File;
import java.io.IOException;

public class VMTranslator {
    public static void main(String[] args) {
        String inputFileName = args[0];
        File inputFile = new File(inputFileName);

        try {
            src.assembler.Parser parser = new Parser(inputFile);
            while (parser.hasMoreLines()) {
                parser.advance();
                System.out.println("현재 명령어 타입: " + parser.commandType());
            }
            System.out.println("검사 종료");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
