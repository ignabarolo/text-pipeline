package com.mypipelineapp.text_pipeline.pipeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Marca esta clase como un servicio de Spring
public class TextProcessingPipeline {

    private final List<TextProcessorStep> steps;

    @Autowired
    public TextProcessingPipeline(List<TextProcessorStep> injectedSteps) {
        // Ordenar los pasos inyectados según su anotación @Order
        injectedSteps.sort(AnnotationAwareOrderComparator.INSTANCE);
        this.steps = injectedSteps;
    }

    public String process(String input) {
        String currentText = input;
        System.out.println("\nIniciando procesamiento de texto: \"" + input + "\"");
        for (TextProcessorStep step : steps) {
            currentText = step.process(currentText);
            System.out.println("Resultado parcial: \"" + currentText + "\"");
        }
        System.out.println("Procesamiento de texto finalizado.");
        return currentText;
    }
}
