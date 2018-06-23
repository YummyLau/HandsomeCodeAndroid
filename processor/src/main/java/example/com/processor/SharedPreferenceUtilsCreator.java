package example.com.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

/**
 * sharedPreference助手类
 * Created by yummyLau on 2018/6/23.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class SharedPreferenceUtilsCreator {

    private void newSharedPrefrences() {

        FieldSpec sApplicationContext =
                FieldSpec.builder(ClassName.get("android.content", "Context"), "sApplicationContext", Modifier.PRIVATE, Modifier.STATIC)
                        .build();

        FieldSpec mSharedPreferences =
                FieldSpec.builder(ClassName.get("android.content", "SharedPreferences"), "mSharedPreferences", Modifier.PRIVATE)
                        .build();

        TypeSpec helperTypeSpec =
                TypeSpec.classBuilder("SharedPreferenceUtil").addModifiers(Modifier.PUBLIC, Modifier.FINAL)

                        .addField(sApplicationContext)
                        .addField(mSharedPreferences)

                        .addMethod(buildPutMethod(String.class))
                        .addMethod(buildPutMethod(Integer.class))
                        .addMethod(buildPutMethod(Long.class))
                        .addMethod(buildPutMethod(Float.class))
                        .addMethod(buildPutMethod(Boolean.class))

                        .addMethod(buildGetMethod(String.class))
                        .addMethod(buildGetMethod(Integer.class))
                        .addMethod(buildGetMethod(Long.class))
                        .addMethod(buildGetMethod(Float.class))
                        .addMethod(buildGetMethod(Boolean.class))

                        .build();
    }

    public static MethodSpec buildGetMethod(Class valueClass) {
        StringBuilder methodName = new StringBuilder("get");
        if (valueClass.getSimpleName().equals(String.class.getSimpleName())) {
            methodName.append("String");
        } else if (valueClass.getSimpleName().equals(Integer.class.getSimpleName())) {
            methodName.append("Int");
        } else if (valueClass.getSimpleName().equals(Long.class.getSimpleName())) {
            methodName.append("Long");
        } else if (valueClass.getSimpleName().equals(Float.class.getSimpleName())) {
            methodName.append("Float");
        } else if (valueClass.getSimpleName().equals(Boolean.class.getSimpleName())) {
            methodName.append("Boolean");
        }
        MethodSpec.Builder method = MethodSpec
                .methodBuilder(valueClass.getName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .returns(TypeName.VOID);
        //添加参数
        method.addParameter(ClassName.get(String.class), "key");
        method.addParameter(ClassName.get(valueClass), "defaultValue");
        //构建方法体
        method.addStatement("return mSharedPreferences.getBoolean(key, defaultValue)");
        return method.build();
    }

    private static MethodSpec buildPutMethod(Class valueClass) {
        StringBuilder methodName = new StringBuilder("put");
        if (valueClass.getSimpleName().equals(String.class.getSimpleName())) {
            methodName.append("String");
        } else if (valueClass.getSimpleName().equals(Integer.class.getSimpleName())) {
            methodName.append("Int");
        } else if (valueClass.getSimpleName().equals(Long.class.getSimpleName())) {
            methodName.append("Long");
        } else if (valueClass.getSimpleName().equals(Float.class.getSimpleName())) {
            methodName.append("Float");
        } else if (valueClass.getSimpleName().equals(Boolean.class.getSimpleName())) {
            methodName.append("Boolean");
        }
        MethodSpec.Builder method = MethodSpec
                .methodBuilder(valueClass.getName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .returns(TypeName.VOID);
        //添加参数
        method.addParameter(ClassName.get(String.class), "key");
        method.addParameter(ClassName.get(valueClass), "value");
        //构建方法体
        method.addStatement("com.content.SharedPreferences.Editor editor = mSharedPreferences.edit()");
        method.addStatement("editor." + methodName.toString() + "(key, value).commit()");
        return method.build();
    }

}
