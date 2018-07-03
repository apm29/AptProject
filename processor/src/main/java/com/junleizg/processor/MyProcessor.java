package com.junleizg.processor;

import com.google.auto.service.AutoService;
import com.junleizg.annotations.FBinder;
import com.junleizg.annotations.Enabler;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

@AutoService(Processor.class)
public class MyProcessor extends AbstractProcessor{

    private Elements mElementUtils;
    private Filer mFiler;
    private Messager mMessager;
    private Types mTypeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
        mTypeUtils = processingEnv.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement element : annotations) {
            System.out.println("==============================");
            System.out.println(element.getSimpleName());
            System.out.println("==============================");
        }
        for (Element element : roundEnv.getElementsAnnotatedWith(Enabler.class)) {
            System.out.println("------------------------------");
            if (element.getKind() == ElementKind.FIELD) {
                VariableElement variableElement = (VariableElement) element;
                System.out.println(variableElement.getSimpleName());
                System.out.println(variableElement.getAnnotation(Enabler.class).value());
                System.out.println(variableElement.getConstantValue());
                System.out.println(variableElement.getEnclosingElement());
            }
            System.out.println("------------------------------");
        }


        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(Enabler.class.getCanonicalName());
        annotations.add(FBinder.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }
}
