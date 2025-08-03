package com.mypipelineapp.text_pipeline.steps;

import com.mypipelineapp.text_pipeline.pipeline.TextProcessorStep;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(30) // Tercer paso: reemplazar abreviaturas (¡antes de eliminar especiales!)
public class ReplaceAbbreviationsStep implements TextProcessorStep {
    @Override
    public String process(String text) {
        System.out.println("Paso: Reemplazar abreviaturas...");
        String processedText = text.replace("ud.", "usted");
        processedText = processedText.replace("atte.", "atentamente");
        return processedText;
    }
}
