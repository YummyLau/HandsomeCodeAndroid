package example.com.apt_processor.sp;


import com.example.spannotation.SharedPreferencesField;
import com.example.spannotation.SharedPreferencesFileName;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * 编译
 * Created by yummyLau on 2018/6/23.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
@AutoService(Process.class)
public class SharedPreferencesProcessor extends AbstractProcessor {

    private Messager messager;
    private Elements elementUtils;
    private static boolean hasCreatedUtils = false;

    private static final String DEFAULT_VALUE = "defaultValue";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
    }
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(SharedPreferencesFileName.class.getCanonicalName());
        annotations.add(SharedPreferencesField.class.getCanonicalName());
        return annotations;
    }



    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        JavaFile javaFile = JavaFile.builder("com.songwenju.aptproject", helloWorld)
                .build();

        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

//        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(SharedPreferencesFileName.class);
//        for (Element element : elements) {
//            if (element.getKind() != ElementKind.CLASS) {
//                messager.printMessage(Diagnostic.Kind.ERROR,
//                        String.format("Only classes can be annotated with @%s.", SharedPreferencesFileName.class.getSimpleName()));
//                return true;
//            }
//            TypeElement typeElement = (TypeElement) element;
//            if (!isValidClass(typeElement)) {
//                return true;
//            }
//
//            //获取 SharedPreferencesFileName 注解
//            SharedPreferencesFileName annotation = typeElement.getAnnotation(SharedPreferencesFileName.class);
//
//            //存储文件key
//            String keyValue = annotation.key();
//            if (keyValue.equals("")) {
//                keyValue = annotation.getClass().getSimpleName().toLowerCase() + "_key";
//            }
//            String key = keyValue.toUpperCase();
//            List<? extends Element> members = elementUtils.getAllMembers(typeElement);
//
//            String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
//            String getUtilInstance = packageName + ".SharedPreferenceUtil.getInstance";
//
//            List<MethodSpec> methodList = new ArrayList<>();
//            for (Element ele : members) {
//                final boolean isVariableElement = ele instanceof VariableElement;
//                if (!isVariableElement) {
//                    continue;
//                }
//
//                //获取 SharedPreferencesField 注解
//                SharedPreferencesField configMember = ele.getAnnotation(SharedPreferencesField.class);
//                if (configMember == null) {
//                    continue;
//                }
//
//                //构建put get 方法
//                methodList.add(buildGetMethod((VariableElement) ele, key, getUtilInstance));
//                methodList.add(buildGetMethod((VariableElement) ele, key, getUtilInstance));
//            }
//
//
//            if (!hasCreatedUtils) {
//                writeNewFile(packageName, SharedPreferenceUtilsCreator.newSharedPrefrences());
//                hasCreatedUtils = true;
//            }
//
//
//            //生成操作类
//            if (!methodList.isEmpty()) {
//                FieldSpec fieldSpec =
//                        FieldSpec.builder(ClassName.get(String.class), key.toUpperCase(), Modifier.FINAL, Modifier.STATIC)
//                        .initializer(CodeBlock.of("\"" + keyValue + "\""))
//                        .build();
//                TypeSpec helperTypeSpec =
//                        TypeSpec.classBuilder(typeElement.getSimpleName().toString() + "_Helper")
//                                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
//                                .addField(fieldSpec)
//                                .addMethods(methodList)
//                                .build();
//                writeNewFile(packageName, helperTypeSpec);
//            }
//        }
//        return true;
    }

    private MethodSpec buildGetMethod(VariableElement member, String configKey, String getUtilInstance) {
        SharedPreferencesField filed = member.getAnnotation(SharedPreferencesField.class);
        if (filed == null) {
            return null;
        }
        String key = member.getSimpleName().toString();
        if (!filed.key().equals("")) {
            key = filed.key();
        }
        String methodName = "get_" + key;
        MethodSpec.Builder getMethod = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC);

        TypeName parameterType = null;
        StringBuffer stringBuffer = new StringBuffer(getUtilInstance);
        stringBuffer.append("(");
        stringBuffer.append(configKey);
        stringBuffer.append(").");
        switch (member.asType().toString()) {
//            case Consts.STRING: {
//                parameterType = ClassName.get(String.class);
//                stringBuffer.append("getString(");
//                break;
//            }
//            case Consts.INTEGER_WRAPPERS:
//            case Consts.INTEGER: {
//                parameterType = ClassName.get(Integer.class);
//                stringBuffer.append("getInt(");
//                break;
//            }
//            case Consts.FLOAT_WRAPPERS:
//            case Consts.FLOAT: {
//                parameterType = ClassName.get(Float.class);
//                stringBuffer.append("getFloat(");
//                break;
//            }
//            case Consts.BOOLEAN_WRAPPERS:
//            case Consts.BOOLEAN: {
//                parameterType = ClassName.get(Boolean.class);
//                stringBuffer.append("getBoolean(");
//                break;
//            }
//            case Consts.LONG_WRAPPERS:
//            case Consts.LONG: {
//                parameterType = ClassName.get(Long.class);
//                stringBuffer.append("getLong(");
//                break;
//            }
            default: {
                parameterType = ClassName.get(String.class);
                stringBuffer.append("getString(");
            }
        }
        stringBuffer.append("\"" + key + "\"" + ",");
        stringBuffer.append(DEFAULT_VALUE);
        stringBuffer.append(")");
        getMethod.returns(parameterType);
        getMethod.addParameter(parameterType, DEFAULT_VALUE);
        getMethod.addStatement(stringBuffer.toString());
        return getMethod.build();
    }

    /**
     * 生成新文件
     *
     * @param packageName
     * @param typeSpec
     */
    private void writeNewFile(String packageName, TypeSpec typeSpec) {
        JavaFile javaFile = JavaFile.builder(packageName, typeSpec).build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 被 SharedPreferencesFileName 注解的类是否满足条件
     *
     * @param typeElement
     * @return
     */
    private boolean isValidClass(TypeElement typeElement) {

        if (!typeElement.getModifiers().contains(Modifier.PUBLIC)) {
            messager.printMessage(Diagnostic.Kind.ERROR,
                    String.format("The class %s is not public.", typeElement.getQualifiedName().toString()));
            return false;
        }

        if (typeElement.getModifiers().contains(Modifier.ABSTRACT)) {
            messager.printMessage(Diagnostic.Kind.ERROR,
                    String.format("The class %s is abstract. You can't annotate abstract classes with @%",
                            typeElement.getQualifiedName().toString(), SharedPreferencesFileName.class.getSimpleName()));
            return false;
        }

        for (Element enclosed : typeElement.getEnclosedElements()) {
            if (enclosed.getKind() == ElementKind.CONSTRUCTOR) {
                ExecutableElement constructorElement = (ExecutableElement) enclosed;
                if (constructorElement.getParameters().size() == 0
                        && constructorElement.getModifiers().contains(Modifier.PUBLIC)) {
                    return true;
                }
            }
        }

        messager.printMessage(Diagnostic.Kind.ERROR,
                String.format("The class %s must provide an public empty default constructor",
                        typeElement.getQualifiedName().toString()));
        return false;
    }
}
