package com.mypipelineapp.text_pipeline.steps;

import com.mypipelineapp.text_pipeline.pipeline.TextProcessorStep;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(20) // Segundo paso: convertir a minúsculas
public class ConvertToLowerCaseStep implements TextProcessorStep {
    @Override
    public String process(String text) {
        System.out.println("Paso: Convertir a minúsculas...");
        return text.toLowerCase();
    }
}
