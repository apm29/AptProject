package com.junleizg.processor;

import com.google.auto.service.AutoService;
import com.junleizg.annotations.CheckField;
import com.junleizg.annotations.EnableField;
import com.junleizg.annotations.CheckMethod;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
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
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;

@AutoService(Processor.class)
public class MyProcessor extends AbstractProcessor {


    private Elements mElementUtils;
    private Filer mFiler;
    private Messager mMessager;
    private Types mTypeUtils;

    /**
     * containers
     */
    private Set<ExecutableElement> checkMethodSet = new HashSet<>();
    private Map<String, InjectorInfo> checkFieldSet = new HashMap<>();
    private PackageElement mPackageElement;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
        mTypeUtils = processingEnv.getTypeUtils();
    }

    /**
     * @param annotations 待处理的注解
     * @param roundEnv    提供
     * @return 返回false 其他注解处理器也可以处理MyProcess处理过的注解
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            Set<? extends Element> checkFieldSet = roundEnv.getElementsAnnotatedWith(CheckMethod.class);
            for (Element element : checkFieldSet) {
                ExecutableElement executableElement = (ExecutableElement) element;
                checkMethodSet.add(executableElement);
                if (mPackageElement == null)
                    mPackageElement = mElementUtils.getPackageOf(executableElement);
            }
            if (checkFieldSet.size()==0) {
                HashSet<MethodSpec> methodSpecs = new HashSet<>();
                for (ExecutableElement executableElement : checkMethodSet) {
                    methodSpecs.add(
                            MethodSpec.methodBuilder(executableElement.getSimpleName().toString())
                                    .build()
                    );
                }

                JavaFile methodFile = JavaFile.builder(
                        mPackageElement.getQualifiedName().toString(),
                        TypeSpec.classBuilder("CheckMethod")
                                .addMethods(methodSpecs)
                                .build()
                ).build();
                methodFile.writeTo(mFiler);
            }

        } catch (Exception e) {
            error(e.getMessage());
        }


        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(EnableField.class.getCanonicalName());
        annotations.add(CheckField.class.getCanonicalName());
        annotations.add(CheckMethod.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }


    private void error(String errorMsg) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, errorMsg);
    }
}
