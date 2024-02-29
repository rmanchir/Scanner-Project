/*
Project submitted by:

Varshini Dunnala- vdunnal@pnw.edu
Rushitha Manchireddy-rmanchir@pnw.edu
Varun Sai Puri- puri17@pnw.edu
Bharath Chirra  - bchirra@pnw.edu

 */


public class Token {
    String token;
    String lexeme;

    public Token(String token, String lexeme) {
        this.token = token;
        this.lexeme = lexeme;
    }

    public String toString() {
        return formateOutPut(lexeme,token);
    }
    String formateOutPut(String l,String t){
        String outPut=l;
        for(int i=l.length() ; i<16 ; i++){
            outPut+=' ';
        }
        return outPut+token;
    }

}