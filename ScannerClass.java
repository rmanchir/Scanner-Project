/*
Project submitted by:

Varshini Dunnala- vdunnal@pnw.edu
Rushitha Manchireddy-rmanchir@pnw.edu
Varun Sai Puri- puri17@pnw.edu
Bharath Chirra  - bchirra@pnw.edu

 */

//importing all the required packages 
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

//
public class ScannerClass {

    BufferedReader readerObj;
    char currentChar;
    List<Token> token_List = new ArrayList<>();

    public static final String KEY_WORDS[] = new String[]{
             "int", "char", "double", "boolean", "long"};

    public ScannerClass(File inputFile) {

        try {
            readerObj = new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        currentChar = readingNextChar();
    }
    //Returns the list of Tokens present in the given input
    List<Token> generateTokens() {
        Token token = readingNextToken();
        while (token != null) {
            token_List.add(token);
            token = readingNextToken();
        }
        return token_List;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScannerClass)) return false;
        ScannerClass that = (ScannerClass) o;
        return currentChar == that.currentChar && Objects.equals(readerObj, that.readerObj) && Objects.equals(token_List, that.token_List);
    }

    @Override
    public int hashCode() {
        return Objects.hash(readerObj, currentChar, token_List);
    }

    Token readingNextToken() {
        int currentState = 1;

        while (true) {
            if (currentChar == (char) (-1)) {
                try {
                    readerObj.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        //Parsing through the given input and categorising the tokens between seperators and operators
            switch (currentState) {
                case 1: {
                    //Checking if the input has any spaces, tab spaces or line spaces and reading the next char if present
                    if (currentChar == '\n' || currentChar == '\r' || currentChar == '\b' ||
                            currentChar == ' ' || currentChar == '\t' || currentChar == '\f') {
                        currentChar = readingNextChar();
                        continue;
                        //Checking if the input has any Semicolon-Seperator and returning it if present
                    } else if (currentChar == ';') {
                        currentChar = readingNextChar();
                        return new Token("Semicolon-Separator", ";");

                        
                    } //Checking if the input has any Coma-Seperator and returning it if present
                    else if (currentChar == ',') {
                        currentChar = readingNextChar();
                        return new Token("Comma - Separator", ",");
                    } //Checking if the char is Equal or Assign Operator and returning the respective operator
                    else if (currentChar == '=') {
                        currentChar = readingNextChar();
                        if (currentChar == '=') {
                            currentChar = readingNextChar();
                            return new Token("Equal Operator", "==");
                        } else {
                            return new Token("Assign Operator", "=");
                        }
                    }//Checking if the char is Not Equal Operator
                     else if (currentChar == '!') {
                        currentChar = readingNextChar();
                        if (currentChar == '=') {
                            currentChar = readingNextChar();
                            return new Token("Not Equal Operator ", "!=");
                        } else return new Token("Not Defined", "!");
                    }//Checking if the input has any Operators and returning  if present
                    else if (currentChar == '+') {
                        currentChar = readingNextChar();
                        if (currentChar == '=') {
                            currentChar = readingNextChar();
                            return new Token("Operator", "+=");
                        } else {
                            return new Token("Plus Operator", "+");
                        }//Checking if the input has any Operators and returning  if present
                    } else if (currentChar == '-') {
                        currentChar = readingNextChar();
                        if (currentChar == '=') {
                            currentChar = readingNextChar();
                            return new Token("Operator", "-=");
                        } else {
                            return new Token("Minus Operator", "-");
                        }//Checking if the input has any Operators and returning  if present
                    } else if (currentChar == '*') {
                        currentChar = readingNextChar();
                        if (currentChar == '=') {
                            currentChar = readingNextChar();
                            return new Token("Operator", "*=");
                        } else {
                            return new Token("Multiplication Operator", "*");
                        }//Checking if the input has any Operators and returning  if present
                    } else if (currentChar == '/') {
                        currentChar = readingNextChar();
                        if (currentChar == '=') {
                            currentChar = readingNextChar();
                            return new Token("Operator", "/=");
                        } else {
                            return new Token("Division Operator", "/");
                        }
                    }
                    else {
                        currentState = 2;
                        continue;
                    }

                }
                case 2: {
                    if (isNumber(currentChar) || currentChar == '_' || currentChar == '.') {
                        String num = String.valueOf(currentChar);
                        String integerLiteral = String.valueOf(currentChar);

                        Character char1 = 'L';
                        Character char2 = 'l';

                        for (; ; ) {
                            currentChar = readingNextChar();
                            if (currentChar == '.') {
                                num += String.valueOf(currentChar);
                            } else if (Objects.equals(currentChar, char1) || Objects.equals(currentChar, char2) || currentChar == '_' || isNumber(currentChar)) {
                                if (num.contains(".")) {
                                    num += String.valueOf(currentChar);
                                } else
                                    integerLiteral += String.valueOf(currentChar);
                            }//Checking for valid Double Literal
                            else {
                                if (num.startsWith("."))
                                    return new Token("Invalid Double Literal", num);
                                else if (num.contains(".")) {
                                    return new Token("Valid Double Literal", num);
                                }//Cheking for Valid Integer Literals
                                else {
                                    if ((integerLiteral.startsWith("0") && integerLiteral.length() > 1) || integerLiteral.startsWith("_") || integerLiteral.endsWith("_")) {
                                        return new Token("Invalid Integer Literal", integerLiteral);
                                    } else
                                        return new Token("Valid Integer Literal", integerLiteral);

                                }
                            }
                        }
                    }else currentState = 3;
        }
                case 3: {
                    //Checking if the char is a number or a letter and categorising into "KEYWORDS" or "IDENTIFIERS"
                    if (isLetter(currentChar)) {
                        String word = String.valueOf(currentChar);
                        for (; ; ) {
                            currentChar = readingNextChar();
                            if (isLetter(currentChar) || isNumber(currentChar)) {

                                 word += String.valueOf(currentChar);

                            } else {

                                    List key_words = Arrays.asList(KEY_WORDS);

                                    if (key_words.contains(word)){
                                        return new Token("Keyword", word);
                                    }

                                    else return new Token("Identifier", word);
                            }
                        }
                    } else {
                        currentChar = readingNextChar();
                        return new Token("Error", "Not Defined " + currentChar);
                    }
                }
            }
        }


    }
   
    boolean isLetter(char ch) {
        if (ch >= 'a' && ch <= 'z')
            return true;
        if (ch >= 'A' && ch <= 'Z')
            return true;

        return false;

    }
    boolean isNumber(char ch) {
        if (ch >= '0' && ch <= '9')
            return true;

        return false;
    }
    char readingNextChar() {
        try {
            return (char) readerObj.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (char) (-1);
    }
  


}