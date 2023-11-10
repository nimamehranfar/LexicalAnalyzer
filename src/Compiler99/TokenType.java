package Compiler99;

public enum TokenType {
    Comment,
    WhiteSpace,
    Tab,
    NewLine,

    Separator,

    Constant,

    Keyword,

    Operator,

    Identifier;


    public boolean isThereWhiteSpace() {
        return this ==Comment || this == NewLine || this == Tab || this == WhiteSpace;
    }
}