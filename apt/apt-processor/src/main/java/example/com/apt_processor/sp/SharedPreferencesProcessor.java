package example.com.apt_processor.sp;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
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

import example.com.apt_annotation.sp.SharedPreferencesField;
import example.com.apt_annotation.sp.SharedPreferencesFileName;

import static example.com.apt_processor.sp.Constants.DEFAULT_VALUE;
import static example.com.apt_processor.sp.Constants.VALUE;

/**
 * SharedPreferences生成代码
 * Created by yummyLau on 2018/6/23.
 * Email: yummyl.lau@gmail.com
 * blog: yummylau.com
 */
@AutoService(Processor.class)
public class SharedPreferencesProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Messager messager;

    @Override
    public Set<String> getSupportedOptions() {
        return Collections.singleton(Constants.MODULE_NAME);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(SharedPreferencesFileName.class.getCanonicalName());
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(SharedPreferencesFileName.class);
        for (Element element : elements) {

            if (element.getKind() != ElementKind.CLASS) {
                messager.printMessage(Diagnostic.Kind.ERROR,
                        String.format("Only classes can be annotated with @%s.", SharedPreferencesFileName.class.getSimpleName()));
                return true;
            }

            TypeElement typeElement = (TypeElement) element;
            if (!isValidClass(typeElement)) {
                return true;
            }

            SharedPreferencesFileName annotation = typeElement.getAnnotation(SharedPreferencesFileName.class);
            String fileName = annotation.name();
            if (fileName.equals("")) {
                fileName = "sp_name_" + annotation.getClass().getSimpleName().toLowerCase();
            }
            String key = fileName.toUpperCase();

            List<? extends Element> members = elementUtils.getAllMembers(typeElement);
            List<MethodSpec> methodList = new ArrayList<>();
            for (Element ele : members) {
                final boolean isVariableElement = ele instanceof VariableElement;
                if (!isVariableElement) {
                    continue;
                }
                SharedPreferencesField configMember = ele.getAnnotation(SharedPreferencesField.class);
                if (configMember == null) {
                    continue;
                }
                methodList.add(buildGetMethod((VariableElement) ele, key));
                methodList.add(buildPutMethod((VariableElement) ele, key));
            }

            if (!methodList.isEmpty()) {
                FieldSpec fieldSpec = FieldSpec.builder(ClassName.get(String.class), key.toUpperCase(),
                        Modifier.FINAL, Modifier.STATIC)
                        .initializer(CodeBlock.of("\"" + fileName + "\""))
                        .build();
                TypeSpec helperTypeSpec =
                        TypeSpec.classBuilder(typeElement.getSimpleName().toString() + "_SPHelper")
                                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                                .addField(fieldSpec)
                                .addMethods(methodList)
                                .build();
                writeNewFile(elementUtils.getPackageOf(typeElement).getQualifiedName().toString(), helperTypeSpec);
            }
        }
        return true;
    }

    private void writeNewFile(String packageName, TypeSpec typeSpec) {
        JavaFile javaFile = JavaFile.builder(packageName, typeSpec).build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MethodSpec buildGetMethod(VariableElement member, String configKey) {
        SharedPreferencesField configMember = member.getAnnotation(SharedPreferencesField.class);
        if (configMember == null) {
            return null;
        }
        String key = member.getSimpleName().toString();
        if (!configMember.key().equals("")) {
            key = configMember.key();
        }
        String methodName = "get_" + key;
        MethodSpec.Builder getMethod = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC);

        TypeName parameterType = null;
        StringBuffer stringBuffer = new StringBuffer("return example.com.apt_code.SharedPreferenceUtil.getInstance(");
        stringBuffer.append(configKey);
        stringBuffer.append(").");
        switch (member.asType().toString()) {
            case Constants.JavaType.STRING: {
                parameterType = ClassName.get(String.class);
                stringBuffer.append("getString(");
                break;
            }
            case Constants.JavaType.INTEGER_SEALED:
            case Constants.JavaType.INTEGER: {
                parameterType = ClassName.get(Integer.class);
                stringBuffer.append("getInt(");
                break;
            }
            case Constants.JavaType.FLOAT_SEALED:
            case Constants.JavaType.FLOAT: {
                parameterType = ClassName.get(Float.class);
                stringBuffer.append("getFloat(");
                break;
            }
            case Constants.JavaType.BOOLEAN_SEALED:
            case Constants.JavaType.BOOLEAN: {
                parameterType = ClassName.get(Boolean.class);
                stringBuffer.append("getBoolean(");
                break;
            }
            case Constants.JavaType.LONG_SEALED:
            case Constants.JavaType.LONG: {
                parameterType = ClassName.get(Long.class);
                stringBuffer.append("getLong(");
                break;
            }
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

    private MethodSpec buildPutMethod(VariableElement member, String configKey) {
        SharedPreferencesField configMember = member.getAnnotation(SharedPreferencesField.class);
        if (configMember == null) {
            return null;
        }
        String key = member.getSimpleName().toString();
        if (!configMember.key().equals("")) {
            key = configMember.key();
        }
        String methodName = "put_" + key;
        MethodSpec.Builder putMethod = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .returns(TypeName.VOID);
        TypeName parameterType = null;
        StringBuffer stringBuffer = new StringBuffer("example.com.apt_code.SharedPreferenceUtil.getInstance(");
        stringBuffer.append(configKey);
        stringBuffer.append(").");
        switch (member.asType().toString()) {
            case Constants.JavaType.STRING: {
                parameterType = ClassName.get(String.class);
                stringBuffer.append("putString(");
                break;
            }
            case Constants.JavaType.INTEGER_SEALED:
            case Constants.JavaType.INTEGER: {
                parameterType = ClassName.get(Integer.class);
                stringBuffer.append("putInt(");
                break;
            }
            case Constants.JavaType.FLOAT_SEALED:
            case Constants.JavaType.FLOAT: {
                parameterType = ClassName.get(Float.class);
                stringBuffer.append("putFloat(");
                break;
            }
            case Constants.JavaType.BOOLEAN_SEALED:
            case Constants.JavaType.BOOLEAN: {
                parameterType = ClassName.get(Boolean.class);
                stringBuffer.append("putBoolean(");
                break;
            }
            case Constants.JavaType.LONG_SEALED:
            case Constants.JavaType.LONG: {
                parameterType = ClassName.get(Long.class);
                stringBuffer.append("putLong(");
                break;
            }
            default: {
                parameterType = ClassName.get(String.class);
                stringBuffer.append("putString(");
            }
        }
        stringBuffer.append("\"" + key + "\"" + ",");
        stringBuffer.append(VALUE + ")");
        putMethod.addParameter(parameterType, VALUE);
        putMethod.addStatement(stringBuffer.toString());
        return putMethod.build();
    }

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
