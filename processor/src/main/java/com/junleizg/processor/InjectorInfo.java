package com.junleizg.processor;

import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

public class InjectorInfo {
    private final String mGeneratedName;
    String SEPARATOR = "$_$";
    String INJECTOR = "Injector";
    private final Elements mElementUtils;
    Set<VariableElement> fields = new HashSet<>();
    private final String mPackageName;
    private TypeElement mElement;
    public InjectorInfo(Elements elementUtils, TypeElement element) {
        mElement = element;
        mElementUtils = elementUtils;
        mPackageName = mElementUtils.getPackageOf(element).getQualifiedName().toString();
        mGeneratedName = getClassName(element,mPackageName)+SEPARATOR+INJECTOR;
    }

    public Element getElement() {
        return mElement;
    }

    public String getGeneratedName() {
        return mGeneratedName;
    }

    public String getBody() {
        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(mPackageName).append(";\n\n")
                .append("import android.content.Intent;\n\n")
                .append("public class ").append(mGeneratedName).append("{\n");

        generateMethodCode(builder);

        builder.append("}\n");

        return builder.toString();
    }

    private void generateMethodCode(StringBuilder builder) {

    }

    private String getClassName(TypeElement element, String packageName) {
        int packageLen = packageName.length() + 1;
        return element.getQualifiedName().toString().substring(packageLen)
                .replace('.', '$');
    }
}
