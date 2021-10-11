package com.kimxavi87.processor;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.HashSet;
import java.util.Set;

@AutoService(Processor.class)
public class MagicProcessor extends AbstractProcessor {
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(Magic.class.getName());
        return set;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(Magic.class);
        for (Element element : elementsAnnotatedWith) {
            Name simpleName = element.getSimpleName();
            if (element.getKind() == ElementKind.INTERFACE) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "process " + simpleName);
            } else {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "can not process " + simpleName);
            }
        }

        return true;
    }
}
