package com.company;

/**
 * Created by Senorita on 1/24/2015.
 */
public class Token {


    public static final int IDENTIFIER = 1;
    public static final int NUMBER = 2;
    public static final int OPENPAREN = 3;
    public static final int CLOSEPAREN = 4;
    public static final int BINBASICOP = 5;
    public static final int BINLOGICOP = 6;
    public static final int COMPARISONOP = 7;
    public static final int BOOL = 8;
    public static final int STRING = 9;
    public static final int UNLOGICOP = 10;
    public static final int UNBASICOP = 11;


    private int type;
    private String lex;

    public Token() {
    }

    public Token(int type, String lex) {
        this.type = type;
        this.lex = lex;
    }

    public int getType() {
        return type;
    }

    public String getLex() {
        return lex;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setLex(String lex) {
        this.lex = lex;
    }

    public static String tokenTypeName(int type){
        switch (type){
            case(Token.BINBASICOP):{
                return "Basic Arithmetic Operator";
            }
            case(Token.BOOL):{
                return "Boolean";
            }
            case(Token.CLOSEPAREN):{
                return "Close Parenthesis";
            }
            case(Token.COMPARISONOP):{
                return "Comparison Operator";
            }
            case(Token.IDENTIFIER):{
                return "Identifier";
            }
//            case(Token.INVALID_TOKEN):{
//                return "INVALID    ";
//            }
            case(Token.BINLOGICOP):{
                return "Logical Operator";
            }
            case(Token.NUMBER):{
                return "Number";
            }
            case(Token.OPENPAREN):{
                return "Open Parenthesis";
            }
            case(Token.STRING):{
                return "String";
            }
            case(Token.UNBASICOP):{
                return "Unary Arithmetic Operator";
            }
            case(Token.UNLOGICOP):{
                return "Unary Logical Operator";
            }
        }
        return "SHOULD NOT APPEAR";
    }
}
