Patrón de Comportamiento: Pipeline (Tubería) en Spring Boot

Este proyecto es un ejemplo práctico y sencillo del Patrón de Comportamiento Pipeline (Tubería), implementado en una aplicación Spring Boot. El objetivo es demostrar cómo se pueden encadenar una serie de operaciones modulares y desacopladas para procesar un flujo de datos.

🌟 Concepto del Patrón Pipeline

El patrón Pipeline, también conocido como Pipes and Filters (Tuberías y Filtros), es un patrón arquitectónico que organiza un sistema para procesar un flujo de datos a través de una secuencia de etapas de procesamiento.

Componentes Clave:

    Filtros (o Pasos): Son unidades de procesamiento individuales y autónomas. Cada filtro toma una entrada, realiza una operación específica y produce una salida. Un filtro no tiene conocimiento de los filtros anteriores o posteriores en la secuencia; solo se preocupa por su propia lógica de transformación.

    Tuberías (o Canal): Conectan los filtros, dirigiendo la salida de un filtro como la entrada del siguiente. Las tuberías se encargan de la orquestación y el flujo de datos.

    Datos (Contexto): La información que fluye a través de la tubería y es procesada por cada filtro.

Ventajas del Patrón Pipeline:

    Modularidad: Cada paso es una unidad independiente, lo que facilita el desarrollo, la comprensión y el mantenimiento.

    Extensibilidad: Añadir nuevas funcionalidades es tan simple como crear un nuevo filtro e insertarlo en la tubería, sin modificar los filtros existentes (cumpliendo el Principio Abierto/Cerrado).

    Flexibilidad: Se pueden construir diferentes pipelines con distintas combinaciones y órdenes de filtros para adaptarse a diversas necesidades.

    Reusabilidad: Los filtros pueden ser reutilizados en múltiples pipelines o en diferentes contextos.

    Facilidad de Prueba: Cada filtro individual puede ser probado de forma aislada.

🚀 El Ejercicio de Ejemplo: Normalización de Texto

En este proyecto, aplicamos el patrón Pipeline para la normalización y limpieza de cadenas de texto. Imaginemos que recibimos texto de diversas fuentes y necesitamos estandarizarlo antes de almacenarlo o usarlo.

Nuestro pipeline de procesamiento de texto consta de los siguientes pasos (filtros):

    RemoveExtraSpacesStep: Elimina espacios en blanco redundantes (múltiples espacios se reducen a uno solo) y recorta los espacios al inicio y al final de la cadena.

    ConvertToLowerCaseStep: Convierte toda la cadena de texto a minúsculas, estandarizando la capitalización.

    RemoveNonAlphanumericStep: Elimina cualquier carácter que no sea alfanumérico. Importante: Esta implementación está configurada para mantener letras del alfabeto inglés (a-zA-Z), números (0-9), caracteres acentuados comunes en español (áéíóúÁÉÍÓÚñÑ) y espacios en blanco (\s).

Estructura del Proyecto

El proyecto está organizado de la siguiente manera:

text-pipeline/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── mypipelineapp/
│   │   │           └── text_pipeline/
│   │   │               ├── controller/
│   │   │               │   └── TextProcessingController.java  <- Endpoint REST
│   │   │               ├── pipeline/
│   │   │               │   ├── TextProcessingPipeline.java    <- La orquestación del Pipeline
│   │   │               │   └── TextProcessorStep.java         <- Interfaz base para cada paso
│   │   │               └── steps/                             <- Implementaciones de los pasos
│   │   │                   ├── ConvertToLowerCaseStep.java
│   │   │                   ├── RemoveExtraSpacesStep.java
│   │   │                   └── RemoveNonAlphanumericStep.java
│   │   │               └── TextPipelineApplication.java       <- Clase principal de Spring Boot
│   │   └── resources/
│   │       └── application.properties
├── build.gradle                                            <- Archivo de configuración de Gradle
├── gradlew                                                 <- Script de Gradle Wrapper (Linux/macOS)
└── gradlew.bat                                             <- Script de Gradle Wrapper (Windows)

Componentes Clave Explicados

    TextProcessorStep (Interfaz): Define el contrato para cada paso del pipeline. Todos los pasos deben implementar el método process(String text), que toma una cadena y devuelve una cadena transformada.

    TextProcessingPipeline (Clase de Servicio):

        Marcada con @Service para que Spring la gestione como un componente.

        Su constructor inyecta automáticamente una List de todas las implementaciones de TextProcessorStep que Spring encuentra en el contexto.

        Utiliza @Order en cada TextProcessorStep y AnnotationAwareOrderComparator en su constructor para garantizar que los pasos se ejecuten en una secuencia predefinida y lógica.

        El método process(String input) itera sobre la lista ordenada de pasos, pasando la salida de uno como entrada al siguiente.

    Clases en steps/:

        Cada clase (ej., RemoveExtraSpacesStep) es un @Component de Spring e implementa TextProcessorStep.

        Contiene la lógica específica para una única transformación de texto.

        La anotación @Order(int) asegura su posición en el pipeline.

    TextProcessingController (Controlador REST):

        Un @RestController que expone un endpoint POST (/api/text/process).

        Recibe una cadena de texto plana como cuerpo de la solicitud (@RequestBody String inputText y consumes = MediaType.TEXT_PLAIN_VALUE).

        Inyecta TextProcessingPipeline y delega el procesamiento del texto a este servicio.

        Utiliza anotaciones de Springdoc OpenAPI (Swagger UI) para documentar automáticamente la API, haciéndola fácilmente explorable y probable desde el navegador.

🛠️ Cómo Ejecutar el Proyecto

    Clonar el Repositorio:
    Bash

git clone [URL_DE_TU_REPOSITORIO]
cd text-pipeline

Construir y Ejecutar la Aplicación:
Asegúrate de tener Java 17+ instalado. El proyecto usa Gradle Wrapper, por lo que no necesitas una instalación global de Gradle.
Bash

    ./gradlew bootRun  # En Linux/macOS
    gradlew.bat bootRun # En Windows

    La aplicación se iniciará por defecto en el puerto 8080.

🧪 Cómo Probar la API

Una vez que la aplicación esté en ejecución, puedes interactuar con el endpoint REST.

Acceder a Swagger UI (Documentación Interactiva):

Abre tu navegador y ve a:
http://localhost:8080/swagger-ui.html

Desde allí, podrás ver la documentación del endpoint /api/text/process, probarlo con diferentes entradas y observar el resultado.

Probar Directamente con cURL (Terminal):

Bash

curl -X POST \
     -H "Content-Type: text/plain" \
     -d "  ESTE   ES  UN EJEMPLO!  DE TEXTO. con Ñ y acentos: áéíóú.  " \
     http://localhost:8080/api/text/process

Salida Esperada (en la consola del servidor y en la respuesta HTTP):

Iniciando procesamiento de texto: "  ESTE   ES  UN EJEMPLO!  DE TEXTO. con Ñ y acentos: áéíóú.  "
Paso: Eliminar espacios extra y recortar...
Resultado parcial: "ESTE ES UN EJEMPLO! DE TEXTO. con Ñ y acentos: áéíóú."
Paso: Convertir a minúsculas...
Resultado parcial: "este es un ejemplo! de texto. con ñ y acentos: áéíóú."
Paso: Eliminar caracteres no alfanuméricos (mantener acentos y espacios)...
Resultado parcial: "este es un ejemplo de texto con ñ y acentos áéíóú"
Procesamiento de texto finalizado.
Texto final procesado devuelto: "este es un ejemplo de texto con ñ y acentos áéíóú"

💡 ¿Por Qué este Patrón?

Este ejemplo simple ilustra la potencia del patrón Pipeline. Si en el futuro necesitaras añadir un paso para, digamos, censurar palabras clave o traducir ciertas frases, simplemente crearías un nuevo TextProcessorStep e incluirías su @Component en el proyecto. Spring lo detectaría automáticamente, y si le das el @Order adecuado, se integraría sin modificar ninguna de las lógicas existentes. Esto fomenta un código más limpio, mantenible y escalable.
