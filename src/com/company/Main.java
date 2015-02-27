package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here

    }

    public static boolean logicalExpressionEvaluator(String expression){
        /**
         * A function that resolves a logical expression
         */
        ArrayList<Token> tokens = lexicalAnalyzer(expression);

        return false;
    }

    public static ArrayList<Token> lexicalAnalyzer(String expression){
        ArrayList<Token> tokens = new ArrayList<Token>();
        Token lastToken = null;
        int originalLength = expression.length();
        while(expression.length() > 0){
            String ch = Character.toString(expression.charAt(0));
            if(ch.equals("(")){ // Open parenthesis
                Token temp = new Token();
                temp.setType(Token.OPENPAREN);
                temp.setLex(ch);
                tokens.add(temp);
                lastToken = temp;
                if(expression.length() > 1) {
                    expression = expression.substring(1);
                }
                else{
                    expression = "";
                }
            }
            else if(ch.equals(")")){ // Close parenthesis
                Token temp = new Token();
                temp.setType(Token.CLOSEPAREN);
                temp.setLex(ch);
                tokens.add(temp);
                lastToken = temp;
                if(expression.length() > 1) {
                    expression = expression.substring(1);
                }
                else{
                    expression = "";
                }
            }
            else if(ch.matches("[A-Za-z_]+")){ // identifier name
                String varName = ch;
                int index = 1;
                if(index < expression.length()) {
                    ch = Character.toString(expression.charAt(index));
                }
                else{
                    ch = "";
                }
                while (index < expression.length() && ch.matches("[A-Za-z_0-9]+")) {
                    varName = varName + ch;
                    index++;
                    if(index < expression.length()) {
                        ch = Character.toString(expression.charAt(index));
                    }
                    else{
                        break;
                    }
                }
                if(index < expression.length()){
                    ch = Character.toString(expression.charAt(index));
                    if(ch.matches("[ )+\\-*/%^<>=]+")){ // valid end_of_id
                        // TODO: FIX BUG WHERE EXPRESSION STARTING WITH "true" goes into identifier name, and exits with null
                        // do nothing
                    }
                    else {
                        //lexicalAnalyzerErrorMessage = "Invalid character at index: " + (originalLength - expression.length() + index) + ", Character: " + ch;
                        if(!varName.equals("true") && !varName.equals("false")) {
                            return null;
                        }
                    }
                }
                Token temp = new Token();
                if(varName.equals("true") || varName.equals("false")){ // boolean
                    temp.setType(Token.BOOL);
                    temp.setLex(varName);
                    tokens.add(temp);
                    lastToken = temp;
                }
                else { // variable name
                    temp.setType(Token.IDENTIFIER);
                    temp.setLex(varName);
                    tokens.add(temp);
                    lastToken = temp;
                }

                if(expression.length() > index) {
                    expression = expression.substring(index);
                }
                else {
                    expression = "";
                }
            }


            else if(ch.matches("[0-9]+")){ // numbers
                String number = ch;
                boolean dotEncountered = false;
                int index = 1;
                if(index < expression.length()) {
                    ch = Character.toString(expression.charAt(index));
                }
                else{
                    ch = "";
                }
                while(index < expression.length() && ch.matches("[0-9.]+")){

                    if(!dotEncountered && ch.equals(".")){
                        dotEncountered = true;
                    }
                    else if(dotEncountered && ch.equals(".")){
                        break;
                    }
                    number = number + ch;
                    index++;
                    if(index < expression.length()){
                        ch = Character.toString(expression.charAt(index));
                    }
                    else {
                        break;
                    }
                }
                if(index < expression.length()){
                    ch = Character.toString(expression.charAt(index));
                    if(ch.matches("[ )+\\-*/%^<>=]+")){ // valid end_of_id
                        // do nothing
                    }
                    else {
                        //lexicalAnalyzerErrorMessage = "Invalid character at index: " + (originalLength - expression.length() + index) + ", Character: " + ch;
                        return null;
                    }
                }

                Token temp = new Token();
                temp.setType(Token.NUMBER);
                temp.setLex(number);
                tokens.add(temp);
                lastToken = temp;
                if(expression.length() > index) {
                    expression = expression.substring(index);
                }
                else{
                    expression = "";
                }
            }
            else if(ch.matches("[+\\-*/%^]+")){ // basic operators
                Token temp = new Token();

                if(lastToken != null && lastToken.getType() != Token.IDENTIFIER && lastToken.getType() != Token.NUMBER
                        && lastToken.getType() != Token.STRING && lastToken.getType() != Token.CLOSEPAREN && ch.matches("[+\\-]+")){
                    temp.setType(Token.UNBASICOP);
                }
                else if(lastToken == null && ch.matches("[+\\-]+")){
                    temp.setType(Token.UNBASICOP);
                }
                else {
                    temp.setType(Token.BINBASICOP);
                }
                temp.setLex(ch);
                tokens.add(temp);
                lastToken = temp;
                if(expression.length() > 1) {
                    expression = expression.substring(1);
                }
                else{
                    expression = "";
                }
            }
            else if(ch.matches("[><=]+")){ // comparison operators, can be <, >, ==, <=, >=, <>
                String nextChar = "";
                if(expression.length() > 1) { // prevents index out of bounds
                    nextChar = Character.toString(expression.charAt(1));
                }
                Token temp = new Token();
                temp.setType(Token.COMPARISONOP);
                if(nextChar.matches("[>=]+")){ // it's a 2-char comparison operator
                    temp.setLex(ch + nextChar);
                    tokens.add(temp);
                    lastToken = temp;
                    if(expression.length() > 2) {
                        expression = expression.substring(2);
                    }
                    else{
                        expression = "";
                    }
                }
                else{
                    temp.setLex(ch);
                    tokens.add(temp);
                    lastToken = temp;
                    if(expression.length() > 1) {
                        expression = expression.substring(1);
                    }
                    else{
                        expression = "";
                    }
                }
            }
            else if(ch.matches("[&|]+")){ // binary logical operators
                Token temp = new Token();
                temp.setType(Token.BINLOGICOP);
                temp.setLex(ch);
                tokens.add(temp);
                lastToken = temp;
                if(expression.length() > 1){
                    expression = expression.substring(1);
                }
                else{
                    expression = "";
                }
            }
            else if(ch.matches("[!]+")){ // unary logical op
                Token temp = new Token();
                temp.setType(Token.UNLOGICOP);
                temp.setLex(ch);
                tokens.add(temp);
                lastToken = temp;
                if(expression.length() > 1){
                    expression = expression.substring(1);
                }
                else {
                    expression = "";
                }
            }
            else if(ch.equals("\"")){ // String, REMEMBER TO CHECK FOR ESCAPE, escaping \ and "
                String str = "";
                String currentChar = "";
                int index = 1;
                while(index < expression.length()){
                    currentChar = Character.toString(expression.charAt(index));
                    if(currentChar.equals("\\")){
                        String nextChar = "";
                        if(expression.length() > index + 1){
                            nextChar = Character.toString(expression.charAt(index+1));
                        }
                        if(nextChar.equals("\"")){
                            str = str + nextChar;
                            index++;
                        }
                        else{
                            str = str + currentChar;
                        }
                    }
                    else if(currentChar.equals("\"")){
                        index++;
                        break;
                    }
                    else {
                        str = str + currentChar;
                    }
                    index++;
                }

                Token temp = new Token();
                temp.setType(Token.STRING);
                temp.setLex(str);
                tokens.add(temp);
                lastToken = temp;
                if(expression.length() > index){
                    expression = expression.substring(index);
                }
                else{
                    expression = "";
                }
            }
            else if(ch.equals(" ")){ // space
                if(expression.length() > 1){
                    expression = expression.substring(1);
                }
                else{
                    expression = "";
                }
            }
            else { // Invalid tokens
//                lexicalAnalyzerErrorMessage = "Invalid character at index: " + (originalLength - expression.length()) + ", Character: " + ch;
                return null;
            }
        }
        return tokens;
    }
}
