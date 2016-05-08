# clj-spin

A simple clojure wrapper library around [SPIN API] (http://topbraid.org/spin/api/). 
The purpose of project is to experiment with SPIN and Clojure and it's under active development ...

## Usage
### Parsing and convert queries
Significant parts of [yesparql](https://github.com/joelkuiper/yesparql) library are used in this examples since clj-spin is dependent on. 
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
(require '[clj-spin.core :refer [init-spin-registry register-all]])
(require '[clj-spin.query :refer [serialize-spin]])

(serialize-spin query "http://example-query.com" "TTL")
```
### Execute SPIN function:

Load ontology with SPIN from http://topbraid.org/examples/spinsquare.ttl ([SPIN Primer: Rectangles and Squares](http://spinrdf.org/spinsquare.html))
```

(def spinsquare (load-model-with-imports "http://topbraid.org/examples/spinsquare.ttl" "TTL"))
```
Initialize SPIN registry and register all SPIN expressions from given ontology
```
(init-spin-registry)
(register-all spinsquare)
```
Lets call "computeArea" function from SPARQL:
```
(def test-spin-f "PREFIX ss: <http://topbraid.org/examples/spinsquare#>
                  SELECT *
                  WHERE {
                    ?rectangle a ss:Rectangle .
                    BIND (ss:computeArea(?rectangle) AS ?area) .}")
```
We can execute given query on spinsquare data previously stored in Jena model. 
```
(require '[clj-spin.query :refer [execute-query]])
(require '[yesparql.sparql :refer [->result result->json]])

(->(execute-query test-spin-f spinsquare)
   (->result)
   (result->json)
   (println))
```
## TODO:
- Inference
- Validation
- Templates
## License

Copyright © 2016 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
