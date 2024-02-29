/*
Project submitted by:

Varshini Dunnala- vdunnala@pnw.edu
Rushitha Manchireddy- rmanchir@pnw.edu
Varun Sai Puri- vpuri17@pnw.edu
Bharath Chirra  - bchirra@pnw.edu

 */

import java.io.File;
import java.net.URL;
import java.util.List;

public class MainClass {
    public static void main(String[] args) {
        
    // Taking the input from the input.txt file present in the same folder without specifying the path
       URL Input_url = ClassLoader.getSystemResource("Input.txt");
        File inputFile = null;

        try {
            //toURI converts the URL into path
            inputFile = new File(Input_url.toURI());
            ScannerClass lexerClass = new ScannerClass(inputFile);

            //generateTokens() method returns the list of tokens available in the given input string
            List<Token> token_List = lexerClass.generateTokens();

            //parsing through each token and printing the token
            for (int token = 0; token < token_List.size(); token++) {
                System.out.println(token_List.get(token).toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}