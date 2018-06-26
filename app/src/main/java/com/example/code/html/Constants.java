package com.example.code.html;

/**
 * 变量
 * created by g8931 on 2018/6/26
 */
public class Constants {

    public interface Tag {
        String FONT = "font";
        String H1 = "h1";
        String B = "b";
        String STRONG = "strong";
        String STRIKE = "strike";
        String DEL = "del";
        String BLOCK_QUOTE = "blockquote";
        String LI = "li";
        String A = "a";
        String IMG = "img";
        String P = "p";
        String HR = "hr";
    }

    public enum ActionType{
        ADD,
        SUPPORT
    }

    public interface LineMargin{
        int NO_BLOCK_LEVEL_LINE_BREAK = 1;
        int BLOCK_LEVEL_LINE_BREAK = 2;
    }



    public interface Attributes{
        String A_HREF = "href";


        String IMG_WIDTH = "width";
        String IMG_HEIGHT = "height";

        String P_ALIGN = "align";
        String P_ALIGN_CENTER = "center";
        String P_ALIGN_LEFT = "left";
        String P_ALIGN_RIGHT = "right";

        String FONT_SIZE = "size";
    }
}
