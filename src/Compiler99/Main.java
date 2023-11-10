package Compiler99;

import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {
        URL path = Main.class.getResource("TestCase1.txt");
        File f = new File(path.getFile());

        StringBuilder fileData = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                fileData.append(line);
            }
        }

        String code = fileData.toString();
        code += "\n";

        LexicalAnalyzer.getInstance().tokenize(code);
        StringBuilder lexResult = new StringBuilder();

        Scanner scr = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Which Analyzer do you prefer?\n 1. Normal Lexical Analayzer\n 2. Normal + WhiteSpaces + Comments");
        int analyzerSelector = scr.nextInt();

        if(analyzerSelector==1) {
            int i = 0;
            for (Token token : LexicalAnalyzer.getInstance().getFilteredTokens()) {
                if (token.getTokenType().isThereWhiteSpace())
                    lexResult.append("   " + token.toString() + "\n");
                else {
                    i++;
                    lexResult.append(i + "   " + token.toString() + "\n");
                }
            }
        }
        else {
            int i = 0;
            for (Token token : LexicalAnalyzer.getInstance().getTokens()) {
                if (token.getTokenType().isThereWhiteSpace())
                    lexResult.append("   " + token.toString() + "\n");
                else {
                    i++;
                    lexResult.append(i + "   " + token.toString() + "\n");
                }
            }
        }
        System.out.print("Your Java Code includes: \n" + lexResult.toString());

    }
}
