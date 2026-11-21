# Diagram klas

Wzorzec **Dekorator**. Klasy w `transforms` to transformacje na Sprint 1 – trzymamy się tych nazw.

```mermaid
classDiagram
    direction TB

    class TextTransformerApplication {
        +main(String[] args)$
    }

    namespace rest {
        class TextTransformerController {
            -TransformerFactory transformerFactory
            +transform(TransformRequest) TransformResponse
            +transform(String text, List~String~ transforms) TransformResponse
            +availableTransformations() Set~String~
            -execute(TransformRequest) TransformResponse
        }
        class RestExceptionHandler {
            +handleUnknownTransformation(UnknownTransformationException) ErrorResponse
        }
        class TransformRequest {
            <<record>>
            String text
            List~String~ transforms
        }
        class TransformResponse {
            <<record>>
            String text
            List~String~ transforms
            String result
        }
        class ErrorResponse {
            <<record>>
            String error
        }
    }

    namespace logic {
        class TextTransformer {
            <<interface>>
            +transform(String text) String
        }
        class PlainText {
            +transform(String text) String
        }
        class TransformerDecorator {
            <<abstract>>
            -TextTransformer wrapped
            +transform(String text) String
            #apply(String text)* String
        }
        class TransformerFactory {
            -Map~String, UnaryOperator~ registry
            +register(String name, UnaryOperator wrapper)
            +create(List~String~ names) TextTransformer
            +availableTransformations() Set~String~
        }
        class UnknownTransformationException {
            -String transformationName
        }
    }

    namespace transforms {
        class UpperCaseTransformer
        class LowerCaseTransformer
        class CapitalizeTransformer
        class InverseTransformer
        class NumberToWordsTransformer
        class AbbreviateTransformer
        class ExpandAbbreviationsTransformer
    }

    TextTransformerController --> TransformerFactory : uses
    TextTransformerController ..> TransformRequest
    TextTransformerController ..> TransformResponse
    RestExceptionHandler ..> ErrorResponse
    RestExceptionHandler ..> UnknownTransformationException

    TextTransformer <|.. PlainText
    TextTransformer <|.. TransformerDecorator
    TransformerDecorator o--> "1" TextTransformer : wrapped
    TransformerFactory ..> TextTransformer : creates
    TransformerFactory ..> UnknownTransformationException : throws

    TransformerDecorator <|-- UpperCaseTransformer
    TransformerDecorator <|-- LowerCaseTransformer
    TransformerDecorator <|-- CapitalizeTransformer
    TransformerDecorator <|-- InverseTransformer
    TransformerDecorator <|-- NumberToWordsTransformer
    TransformerDecorator <|-- AbbreviateTransformer
    TransformerDecorator <|-- ExpandAbbreviationsTransformer
```

Żądanie `["inverse", "upper"]` daje `UpperCaseTransformer(InverseTransformer(PlainText))`.
Każdy dekorator najpierw puszcza tekst przez owinięty obiekt, potem robi swoje `apply`,
więc kolejność jest taka jak w żądaniu.
