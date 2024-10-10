package ru.nsu.fitkulin;

public class Parser {
    private static String expression;
    private static String token;
    private static int pos;
    private static int len;

    static Expression parse(String exp) {
        expression = exp;
        pos = 0;
        len = exp.length();

        return parseExpression();
    }

    private static String readToken() {
        while (pos < len && expression.charAt(pos) == ' ')
            pos++;

        if (pos == len)
            return token = "";

        if ("+-*/()".contains(String.valueOf(expression.charAt(pos))))
            return token = String.valueOf(expression.charAt(pos++));

        int left = pos;
        while (pos < len && Character.isLetterOrDigit(expression.charAt(pos)))
            pos++;
        return token = expression.substring(left, pos);
    }

    private static String peekToken() {
        int oldPos = pos;
        token = readToken();
        pos = oldPos;
        return token;
    }

    private static Expression parseAtom() {
        if (peekToken().equals("(")) {
            readToken();
            Expression ex = parseExpression();
            readToken();
            return ex;
        } else {
            readToken();
            if (token.charAt(0) >= '0' && token.charAt(0) <= '9') {
                return new Number(Integer.parseInt(token));
            } else {
                return new Variable(token);
            }
        }
    }

    private static Expression parseMonome() {
        Expression expL = parseAtom();
        String oper;
        Expression expR;
        while (peekToken().equals("*") || peekToken().equals("/")) {
            oper = readToken();
            expR = parseAtom();
            if (oper.equals("*"))
                expL = new Mul(expL, expR);
            else
                expL = new Div(expL, expR);
        }
        return expL;
    }

    private static Expression parseExpression() {
        Expression expL = parseMonome();
        String oper;
        Expression expR;
        while (peekToken().equals("+") || peekToken().equals("-")) {
            oper = readToken();
            expR = parseMonome();
            if (oper.equals("+"))
                expL = new Add(expL, expR);
            else
                expL = new Sub(expL, expR);
        }
        return expL;
    }
}