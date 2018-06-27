package example.com.apt_processor.sp;

/**
 * 常量管理
 * Created by yummyLau on 2018/6/23.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class Constants {

    public static final String VALUE = "value";
    public static final String DEFAULT_VALUE = "defaultValue";
    public static final String MODULE_NAME = "moduleName";

    public interface JavaType {
        String LANG = "java.lang";
        String BYTE = LANG + ".Byte";
        String SHORT = LANG + ".Short";
        String INTEGER_SEALED = LANG + ".Integer";
        String INTEGER = "int";
        String LONG_SEALED = LANG + ".Long";
        String LONG = "long";
        String FLOAT_SEALED = LANG + ".Float";
        String FLOAT = "float";
        String DOUBLE_SEALED = LANG + ".Double";
        String DOUBLE = LANG + "double";
        String BOOLEAN_SEALED = LANG + ".Boolean";
        String BOOLEAN = "boolean";
        String STRING = LANG + ".String";
        String CHARSEQUENCE = LANG + ".CharSequence";
    }

}
