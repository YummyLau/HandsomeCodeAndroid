package com.example.code.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * json转化
 * Created by yummyLau on 2018/4/15.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    /**
     * 根据map生成Json字符串
     *
     * @param map Map数据
     * @return Json字符串
     */
    public static String getJsonStr(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        String str = null;
        try {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                jsonObject.put(key, value);
            }
            str = jsonObject.toString();
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }
        return str;
    }

    /**
     * 获取JSONObject
     *
     * @param jsonObject
     * @param name
     * @return
     */
    public static JSONObject getJSONObject(JSONObject jsonObject, String name) {
        JSONObject value = null;
        try {
            if (jsonObject.has(name)) {
                value = jsonObject.getJSONObject(name);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return value;
    }

    /**
     * 获取JSONArray
     *
     * @param jsonObject
     * @param name
     * @return
     */
    public static JSONArray getJSONArray(JSONObject jsonObject, String name) {
        JSONArray value = null;
        try {
            if (jsonObject.has(name)) {
                value = jsonObject.getJSONArray(name);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return value;
    }

    /**
     * 获取字符串
     *
     * @param jsonObject
     * @param name
     * @param defaultValue
     * @return
     */
    public static String getString(JSONObject jsonObject, String name, String defaultValue) {
        try {
            if (jsonObject.has(name) && !jsonObject.isNull(name)) {
                return jsonObject.optString(name, defaultValue);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return defaultValue;
    }

    /**
     * 获取已过滤HTML特殊字符的字符串
     *
     * @param jsonObject
     * @param name
     * @return
     */
    public static String getStringFilterHTMLTag(JSONObject jsonObject, String name, String defaultValue) {
        return StringUtils.filterHTMLTag(getString(jsonObject, name, defaultValue));
    }

    /**
     * 获取 Double
     *
     * @param jsonObject
     * @param name
     * @param defaultValue
     * @return
     */
    public static Double getDouble(JSONObject jsonObject, String name, double defaultValue) {
        try {
            if (jsonObject.has(name)) {
                return jsonObject.optDouble(name, defaultValue);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return defaultValue;
    }

    /**
     * 获取 Int
     *
     * @param jsonObject
     * @param name
     * @param defaultValue
     * @return
     */
    public static Integer getInt(JSONObject jsonObject, String name, int defaultValue) {
        try {
            if (jsonObject.has(name)) {
                return jsonObject.optInt(name, defaultValue);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return defaultValue;
    }

    /**
     * 获取 Boolean
     *
     * @param jsonObject
     * @param name
     * @param defaultValue
     * @return
     */
    public static Boolean getBoolean(JSONObject jsonObject, String name, boolean defaultValue) {
        try {
            if (jsonObject.has(name)) {
                return jsonObject.optBoolean(name, defaultValue);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return defaultValue;
    }

    /**
     * 获取 Long
     *
     * @param jsonObject
     * @param name
     * @param defaultValue
     * @return
     */
    public static Long getSaveLong(JSONObject jsonObject, String name, Long defaultValue) {
        try {
            if (jsonObject.has(name)) {
                return jsonObject.optLong(name, defaultValue);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return defaultValue;
    }

    /**
     * 格式化json字符串
     *
     * @param jsonStr 需要格式化的json串
     * @return 格式化后的json串
     */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            //遇到{ [换行，且下一行缩进
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                //遇到} ]换行，当前行缩进
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                //遇到,换行
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }
        return sb.toString();
    }

    /**
     * 添加space
     *
     * @param sb
     * @param indent
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }

    /**
     * http 请求数据返回 json 中中文字符为 unicode 编码转汉字转码
     *
     * @param theString
     * @return 转化后的结果.
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }
}
