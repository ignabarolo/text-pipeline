package com.mypipelineapp.text_pipeline.steps;

import com.mypipelineapp.text_pipeline.pipeline.TextProcessorStep;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(10) // Primer paso: eliminar espacios extra
public class RemoveExtraSpaceStep implements TextProcessorStep {
    @Override
    public String process(String text) {
        System.out.println("Paso: Eliminar espacios extra...");
        return text.replaceAll("\\s+", " ").trim();
    }
}
