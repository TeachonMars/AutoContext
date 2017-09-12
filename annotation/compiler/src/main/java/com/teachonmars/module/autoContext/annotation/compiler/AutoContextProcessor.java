package com.teachonmars.module.autoContext.annotation.compiler;


import android.content.Context;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.teachonmars.module.autoContext.annotation.Constant;
import com.teachonmars.module.autoContext.annotation.NeedContext;

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
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class AutoContextProcessor extends AbstractProcessor {
    private HashMap<Integer, List<Element>> methodList = new HashMap<>();

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(NeedContext.TAG);
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
            TypeSpec.Builder classFile = createFile();
            MethodSpec.Builder mainMethod = createMainMethod();
            for (Map.Entry<Integer, List<Element>> priorityMethodsPair : methodList.entrySet()) {
                String name = Constant.baseNameCommonMethod + priorityMethodsPair.getKey();
                MethodSpec.Builder method = buildMethod(name);
                List<Element> priorityList = priorityMethodsPair.getValue();
                for (Element element : priorityList) {
                    addCall(method, element);
                }
                mainMethod.addStatement("$L($L)", name, Constant.contextParameter);
                classFile.addMethod(method.build());
            }
            TypeSpec classToWrite = finaliseClass(classFile, mainMethod.build());
            writeClass(classToWrite);
        }
    }

    private TypeSpec.Builder createFile() {
        return TypeSpec.classBuilder(Constant.baseBuildedClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
    }

    private MethodSpec.Builder createMainMethod() {
        return MethodSpec.methodBuilder(Constant.buildedClassMain)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(Context.class, Constant.contextParameter);
    }

    private TypeSpec finaliseClass(TypeSpec.Builder classFile, MethodSpec mainMethod) {
        return classFile
                .addMethod(mainMethod)
                .build();
    }

    private void writeClass(TypeSpec classFile) {
        try {
            JavaFile.builder(Constant.basePackageName, classFile)
                    .build()
                    .writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MethodSpec.Builder buildMethod(String name) {
        return MethodSpec
                .methodBuilder(name)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .returns(void.class)
                .addParameter(Context.class, Constant.contextParameter);
    }

    private void addCall(MethodSpec.Builder method, Element element) {
        method.addStatement("$T.$L($L)", element.getEnclosingElement().asType(), element.getSimpleName(), Constant.contextParameter);
    }
}
