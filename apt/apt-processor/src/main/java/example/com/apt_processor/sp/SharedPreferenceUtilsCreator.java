package example.com.apt_processor.sp;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

/**
 * sharedPreference助手类
 * Created by yummyLau on 2018/6/23.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
public class SharedPreferenceUtilsCreator {

    public static TypeSpec newSharedPrefrences() {

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

                        .addMethod(MethodInstanceMethod())
                        .addMethod(MethodInstanceMethod2())

                        .addMethod(buildConstructorMethod())
                        .addMethod(buildInitMethod())

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

        return helperTypeSpec;
    }

    public static MethodSpec MethodInstanceMethod(){
        MethodSpec.Builder method = MethodSpec
                .methodBuilder("getInstance")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(String.class);
        //添加参数
        method.addParameter(ClassName.get(String.class), "preferenceFileName");
        //构建方法体
        method.addStatement("return getInstance(preferenceName, Context.MODE_PRIVATE)");
        return method.build();
    }

    public static MethodSpec MethodInstanceMethod2(){
        MethodSpec.Builder method = MethodSpec
                .methodBuilder("getInstance")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(String.class);
        //构建方法体
        method.addStatement("WeakReference<SharedPreferenceUtil> weakReference = sInstances.get(preferenceName)");
        method.addStatement("SharedPreferenceUtil sharedPreferenceUtil = weakReference == null ? null : weakReference.get()");
        method.beginControlFlow("if (sharedPreferenceUtil == null)");
        method.addStatement("sharedPreferenceUtil = new SharedPreferenceUtil(sApplicationContext.getSharedPreferences(preferenceName, mode))");
        method.addStatement("sInstances.put(preferenceName, new WeakReference<>(sharedPreferenceUtil))");
        method.endControlFlow();
        method.addStatement("return sharedPreferenceUtil");
        return method.build();
    }

    public static MethodSpec buildConstructorMethod(){
        MethodSpec.Builder method = MethodSpec
                .methodBuilder("SharedPreferenceUtil")
                .addModifiers(Modifier.PRIVATE)
                .returns(TypeName.VOID);
        //添加参数
        method.addParameter(ClassName.get("android.content", "SharedPreferences"), "sharedPreferences");
        //构建方法体
        method.addStatement("mSharedPreferences = sharedPreferences");
        return method.build();
    }

    public static MethodSpec buildInitMethod(){
        MethodSpec.Builder method = MethodSpec
                .methodBuilder("init")
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .returns(TypeName.VOID);
        //添加参数
        method.addParameter(ClassName.get("android.content", "Context"), "context");
        //构建方法体
        method.addStatement("      if (context == null) {\n" +
                "                throw new RuntimeException(TAG + \"#init: context can't be null !\");\n" +
                "            }");
        method.addStatement("sApplicationContext = context");
        return method.build();

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
                .methodBuilder(methodName.toString())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .returns(String.class);
        method.addParameter(ClassName.get(String.class), "key");
        method.addParameter(ClassName.get(valueClass), "defaultValue");
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
                .methodBuilder(methodName.toString())
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
