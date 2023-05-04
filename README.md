
# CLOCERTO PLAYGROUND


<h2>    
<hr style="opacity:0">
Clocerto Playground is a development repo for the Clocerto Clojure Library. It is a library that allows for use of the Concerto model language by the Accord Project in Clojure projects.
<hr style="opacity:0">
</h2>

<h1>
<a href="https://www.accordproject.org/projects/concerto">
Concerto
</a>
</h1>


Concerto is a lightweight schema language and runtime for business concepts. Concerto is part of [Accord Project](https://accordproject.org/) a suite of open source tools for smart legal contracts.

A lightweight schema language and runtime for business concepts.

```cs
concept Person identified by name  {
  o String name
  o Address address optional
  @description("Height (cm)")
  o Double height range=[0,]
  o DateTime dateOfBirth 
}
```

🏢 Concerto gives you “just enough” expressivity to capture real-world business models, while remaining easy to map to most runtime environments.

⛳ An object-oriented language that is much easier to read and write than JSON/XML Schema, XMI or equivalents.

📄 Serialize your instances to JSON

🍪 Deserialize (and validate) instances from JSON

🔎 Introspect the model using a [powerful set of APIs](https://docs.accordproject.org/docs/model-api.html)

🎛 Convert the model to other formats:
- JSON Schema
- XML Schema
- OData CDSL
- GraphQL Schema
- Java Classes
- Go Types
- C# Classes
- TypeScript Classes
- Protobuf Messages
- PlantUML Diagrams
- Mermaid UML Diagrams
- Markdown (with embedded Mermaid)
- OpenAPI v3 specification document
- Apache Avro

🕸 Publish your reusable models to any website, including the Accord Project [model repository](https://models.accordproject.org)

Infer models from other formats:
- JSON document
- JSON Schema
- OpenAPI v3 specification document

<hr>
Concerto models may be fully represented via the metamodel JSON format.

Given the following `test.cto` file:

```js
namespace test@1.0.0

concept Person identified by email {
  o String email
  o DateTime dob optional
}
```

It can be converted to metamodel JSON format via the command:

> Note: the `excludeLineLocations` argument removes the text stream positional information from the output JSON

```bash
concerto parse --model test.cto --excludeLineLocations
```

Giving the output:

```json
{
    "$class": "concerto.metamodel@1.0.0.Model",
    "decorators": [],
    "namespace": "test@1.0.0",
    "imports": [],
    "declarations": [
        {
            "$class": "concerto.metamodel@1.0.0.ConceptDeclaration",
            "name": "Person",
            "isAbstract": false,
            "properties": [
                {
                    "$class": "concerto.metamodel@1.0.0.StringProperty",
                    "name": "email",
                    "isArray": false,
                    "isOptional": false
                },
                {
                    "$class": "concerto.metamodel@1.0.0.DateTimeProperty",
                    "name": "dob",
                    "isArray": false,
                    "isOptional": true
                }
            ],
            "identified": {
                "$class": "concerto.metamodel@1.0.0.IdentifiedBy",
                "name": "email"
            }
        }
    ]
}
```
A metamodel JSON document can be converted back to CTO format using the CLI command:

```bash
concerto print --input ./docs/test.json
```

Which will echo the CTO to the console:

```js
namespace test@1.0.0

concept Person identified by email {
  o String email
  o DateTime dob optional
}
```

<h1>
<a href="https://github.com/metosin/malli">
<hr>
Malli
</a>
</h1>
<br>
Concerto operates in an object oriented world of javascript. In the Clojure world <a href="https://www.youtube.com/watch?v=rI8tNMsozo0">
Use map instead of class to represent data" -Rich Hickey
</a> 
<p></p>
<p>The best tool for dealing with schemas as data in Clojure is Malli</p>
Data-driven Schemas for Clojure/Script and [babashka](#babashka).

[Metosin Open Source Status: Active](https://github.com/metosin/open-source/blob/main/project-status.md#active). Stability: well matured [*alpha*](#alpha).

<img src="https://raw.githubusercontent.com/metosin/malli/master/docs/img/malli.png" width=130 align="right"/>

- Schema definitions as data
- [Vector](#vector-syntax), [Map](#map-syntax) and [Lite](#lite) syntaxes
- [Validation](#validation) and [Value Transformation](#value-transformation)
- First class [Error Messages](#error-messages) with [Spell Checking](#spell-checking)
- [Generating values](#value-generation) from Schemas
- [Inferring Schemas](#inferring-schemas) from sample values and [Destructuring](#destructuring).
- Tools for [Programming with Schemas](#programming-with-schemas)
- [Parsing](#parsing-values), [Unparsing](#unparsing-values) and [Sequence Schemas](#sequence-schemas)
- [Persisting schemas](#persisting-schemas), even [function schemas](#serializable-functions)
- Immutable, Mutable, Dynamic, Lazy and Local [Schema Registries](#schema-registry)
- [Schema Transformations](#schema-Transformation) to [JSON Schema](#json-schema), [Swagger2](#swagger2), and [descriptions in english](#description)
- [Multi-schemas](#multi-schemas), [Recursive Schemas](#recursive-schemas) and [Default values](#default-values)
- [Function Schemas](docs/function-schemas.md) with dynamic and static schema checking
   - Integrates with both [clj-kondo](#clj-kondo) and [Typed Clojure](#static-type-checking-via-typed-clojure) 
- Visualizing Schemas with [DOT](#dot) and [PlantUML](#plantuml)
- Pretty [development time errors](#pretty-errors)
- [Fast](#performance)






