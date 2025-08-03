package com.mypipelineapp.text_pipeline.controller;

import com.mypipelineapp.text_pipeline.pipeline.TextProcessingPipeline;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Marca esta clase como un controlador REST
@RequestMapping("/api/text") // Define el prefijo de la URL para este controlador
public class TextProcessingController {
    private final TextProcessingPipeline pipeline;

    @Autowired
    public TextProcessingController(TextProcessingPipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Operation(summary = "Procesa una cadena de texto", description = "Aplica una serie de transformaciones básicas (espacios, minúsculas, caracteres especiales) para normalizar el texto de entrada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Texto procesado exitosamente",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                            schema = @Schema(type = "string", example = "hola mundo como esta"))), // Ejemplo actualizado
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content)
    })
    @PostMapping(value = "/process", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String processText(
            @Parameter(description = "Cadena de texto a procesar", required = true, example = "  Hola  Mundo!   Cómo está.?   ") // Ejemplo actualizado
            @RequestBody String inputText) {
        System.out.println("Recibida solicitud para procesar: \"" + inputText + "\"");
        String processedText = pipeline.process(inputText);
        System.out.println("Texto final procesado devuelto: \"" + processedText + "\"");
        return processedText;
    }
}
