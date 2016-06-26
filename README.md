# clj-spin

A simple clojure wrapper library around [SPIN API] (http://topbraid.org/spin/api/) inspired and dependent on [yesparql](https://github.com/joelkuiper/yesparql). 
The aim of this project is to experiment with SPIN API and Clojure. It's under active development ...

## Usage

[![Clojars Project]
(https://img.shields.io/clojars/v/clj-spin.svg)](https://clojars.org/clj-spin)

### Parse and convert queries
Create SPARQL query string:

```
;; create sparql query string

(def query "PREFIX ex: <http://example.org/demo#>
            SELECT ?person 
            WHERE { 
                ?person a ex:Person . 
                ?person ex:age ?age . 
                FILTER (?age > 18)}")
```

Serialize to SPIN Turtle representation and the URI of the new Query resource (or null for a blank node):

```
(require '[clj-spin.query :refer [serialize-spin]])

(serialize-spin query "http://example-query.com" "TTL")
```

### Execute SPIN function:

Load ontology with SPIN from [http://topbraid.org/examples/spinsquare.ttl](http://topbraid.org/examples/spinsquare.ttl) ([SPIN Primer: Rectangles and Squares](http://spinrdf.org/spinsquare.html))

```
(require '[clj-spin.utils :refer [load-model-with-imports]])

(def spinsquare (load-model-with-imports "http://topbraid.org/examples/spinsquare.ttl" "TTL"))
```

Initialize SPIN registry and register all SPIN expressions from given ontology

```
(require '[clj-spin.core :refer [init-spin-registry register-all]])

(init-spin-registry)
(register-all spinsquare)
```

Lets call `computeArea` function from SPARQL:

```
(def test-spin-f  "PREFIX ss: <http://topbraid.org/examples/spinsquare#>
                   SELECT *
                   WHERE {
                     ?rectangle a ss:Rectangle .
                     BIND (ss:computeArea(?rectangle) AS ?area) .}")
```

We can execute given query on `spinsquare` data previously stored in Jena model. 

```
(require '[clj-spin.query :refer [execute-query]])
(require '[yesparql.sparql :refer [->result result->json]])

(->(execute-query test-spin-f spinsquare)
   (->result)
   (result->json)
   (println))
```

### Inference

Fetching all rules defined in spinsquare ontology with `get-rules` will return a map of class URIs as keys and lists of corresponding queries as values.


```
(require '[clj-spin.utils :refer [create-default-model get-rules]])

(println (get-rules spinsquare))
```

Create empty model to store inferred triples, add it as submodel to main ontology model add apply `run-inferences`: 

```
(require '[clj-spin.inferences :refer [run-inferences]])
(require '[yesparql.sparql :refer [model->ttl]])

(def new-triples (create-default-model))
(.addSubModel spinsquare new-triples)
(register-all spinsquare)
(run-inferences spinsquare new-triples)
(println (model->ttl new-triples))
```

### Constraints

Run `check-constraints` to get the list of SPIN ConstraintViolation objects:

```
(use '[clj-spin.constraints])

(check-constraints spinsquare)
```

To get root resource of violation with corresponding messages we can use `get-root` and `get-messages`: 

```
(require '[clj-spin.utils :refer [create-default-model get-rules]])

(map #(str ("Constraint violations for" get-label (get-root %)) ": "  (get-message %)) (check-constraints spinsquare))
```


## TODO:
- Templates
## License

Copyright © 2016 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
