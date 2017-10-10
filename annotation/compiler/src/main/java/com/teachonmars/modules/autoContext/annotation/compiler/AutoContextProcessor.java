package com.teachonmars.modules.autoContext.annotation.compiler;


import android.content.Context;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.teachonmars.modules.autoContext.annotation.Constant;
import com.teachonmars.modules.autoContext.annotation.NeedContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;


/**
 * Class called at compilation time (of project using this library) to build ContextNeedy class, containing call to all public static methods annotated with {@link NeedContext}
 */
@AutoService(Processor.class)
public class AutoContextProcessor extends AbstractProcessor {
    private HashMap<Integer, List<Element>> methodList = new HashMap<>();

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(NeedContext.TAG);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (!roundEnvironment.processingOver()) {
            processClasses(roundEnvironment);
        } else {
            generateCode();
        }
        return true;
    }

    private void processClasses(RoundEnvironment roundEnvironment) {
        Iterable<? extends Element> items = roundEnvironment.getElementsAnnotatedWith(NeedContext.class);
        for (Element elem : items) {
            switch (elem.getKind()) {
//                case CLASS:
//                    break;
//                case CONSTRUCTOR:
//                    break;
                case METHOD:
                    handleMethod(elem);
                    break;
            }
        }
    }

    private void handleMethod(Element elem) {
        Set<Modifier> modifiers = elem.getModifiers();
        if (modifiers.contains(Modifier.PUBLIC) && modifiers.contains(Modifier.STATIC)) {
            int priority = elem.getAnnotation(NeedContext.class).priority();
            List<Element> priorityList = getListForPriority(priority);
            priorityList.add(elem);
        }
    }

    private List<Element> getListForPriority(Integer priority) {
        List<Element> priorityList = methodList.get(priority);
        if (priorityList == null) {
            priorityList = new ArrayList<>();
            methodList.put(priority, priorityList);
        }
        return priorityList;
    }

    private void generateCode() {
        if (!methodList.isEmpty()) {
            TypeSpec.Builder classFile = createContextNeedyClassFile();
            MethodSpec.Builder mainMethod = createMainMethod();
            addMethodsByPriority(classFile, mainMethod);
            writeClass(finaliseClass(classFile, mainMethod.build()));
        }
    }

    private void addMethodsByPriority(TypeSpec.Builder classFile, MethodSpec.Builder mainMethod) {
        List<Element> notPrioritizedMethods = methodList.remove(0);
        for (Map.Entry<Integer, List<Element>> priorityMethodsPair : methodList.entrySet()) {
            addSingleMethodByPriority(classFile, mainMethod, priorityMethodsPair.getValue(), Constant.baseNameCommonMethod + priorityMethodsPair.getKey());
        }
        if (notPrioritizedMethods != null) {
            addSingleMethodByPriority(classFile, mainMethod, notPrioritizedMethods, buildNoPriorityMethodName());
        }
    }

    private void addSingleMethodByPriority(TypeSpec.Builder classFile, MethodSpec.Builder mainMethod, List<Element> methodList, String methodName) {
        MethodSpec.Builder method = buildMethod(methodName);
        for (Element element : methodList) {
            addCallInClass(method, element);
        }
        mainMethod.addStatement("$L($L)", methodName, Constant.contextParameter);
        classFile.addMethod(method.build());
    }

    private TypeSpec.Builder createContextNeedyClassFile() {
        return TypeSpec.classBuilder(Constant.baseBuiltClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
    }

    private MethodSpec.Builder createMainMethod() {
        return MethodSpec.methodBuilder(Constant.builtClassMain)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(Context.class, Constant.contextParameter);
    }

    private MethodSpec.Builder buildMethod(String name) {
        return MethodSpec
                .methodBuilder(name)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .returns(void.class)
                .addParameter(Context.class, Constant.contextParameter);
    }

    private void addCallInClass(MethodSpec.Builder method, Element element) {
        method.addStatement("$T.$L($L)", element.getEnclosingElement().asType(), element.getSimpleName(), Constant.contextParameter);
    }

    private void writeClass(TypeSpec classFile) {
        try {
            JavaFile.builder(Constant.basePackageName, classFile)
                    .build()
                    .writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Error while writing AutoContext generated class : " + e.getMessage());
        }
    }

    private TypeSpec finaliseClass(TypeSpec.Builder classFile, MethodSpec mainMethod) {
        return classFile
                .addMethod(mainMethod)
                .build();
    }

    private String buildNoPriorityMethodName() {
        return "no" + Constant.baseNameCommonMethod.substring(0, 1).toUpperCase() + Constant.baseNameCommonMethod.substring(1)
    }
}
