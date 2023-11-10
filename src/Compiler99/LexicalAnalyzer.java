package Compiler99;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer {

    private static LexicalAnalyzer lexerSingleton=new LexicalAnalyzer();
    private Map<TokenType, String> regEx;
    private List<Token> result;

    public static LexicalAnalyzer getInstance()
    {
        return lexerSingleton;
    }

    public LexicalAnalyzer() {
        regEx = new TreeMap<TokenType, String>();
        launchRegEx();
        result = new ArrayList<Token>();
    }


    public void tokenize(String source) {
        int position = 0;
        Token token = null;
        do {
            token = separateToken(source, position);
            if (token != null) {
                position = token.getEnd();
                result.add(token);
            }
        } while (token != null && position != source.length());
    }


    public List<Token> getTokens() {
        return result;
    }


    public List<Token> getFilteredTokens() {
        List<Token> filteredResult = new ArrayList<Token>();
        for (Token t : this.result) {
            if (!t.getTokenType().isThereWhiteSpace()) {
                filteredResult.add(t);
            }
        }
        return filteredResult;
    }


    private Token separateToken(String source, int fromIndex) {
        for (TokenType tokenType : TokenType.values()) {
            Pattern p = Pattern.compile(".{" + fromIndex + "}" + regEx.get(tokenType),
                    Pattern.DOTALL);
            Matcher m = p.matcher(source);
            if (m.matches()) {
                String lexeme = m.group(1);
                return new Token(fromIndex, fromIndex + lexeme.length(), lexeme, tokenType);
            }
        }

        return null;
    }


    private void launchRegEx() {
        regEx.put(TokenType.Comment, "(/\\*.*?\\*/|//(.*?)[\r$]?\n).*");
        regEx.put(TokenType.WhiteSpace, "( ).*");
        regEx.put(TokenType.Tab, "(\\t).*");
        regEx.put(TokenType.NewLine, "(\\n).*");

        regEx.put(TokenType.Separator, "(\\(|\\)|;|,|\\.|\\{|\\}).*");

        regEx.put(TokenType.Constant, "\\b(\\d{1,9}\\.\\d{1,32}|\\d{1,9}|null|false|true)\\b.*");

        regEx.put(TokenType.Keyword, "\\b(ArrayList|int|double|void|public|private|return|new|class|if|else|while|static|abstract|assert|boolean|break|byte|case|catch|char|class|continue|default|do|for|enum|extends|final|finally|float|implements|const|import|instanceof|interface|long|native|package|protected|short|strictfp|super|switch|synchronized|this|throw|try|volatile)\\b.*");

        regEx.put(TokenType.Operator, "(\\+{1}|\\-{1}|\\*|/|==|=|\\!=|>|<|\\-=|\\+=|\\*=|/=|\\+\\+|\\-\\-|%|%=).*");

        regEx.put(TokenType.Identifier, "\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*");
    }
}