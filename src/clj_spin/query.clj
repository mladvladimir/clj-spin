(ns clj-spin.query
  ;(:require [yesparql.sparql :refer [model->ttl
  ;                                   serialize-model
  ;                                   result->json
  ;                                   ->CloseableModel
  ;                                   query-exec
  ;                                   query-type
  ;                                   query*
  ;                                   ->triples
  ;                                   ->model]])
  (:use yesparql.sparql)

  (:import [org.topbraid.spin.arq ARQ2SPIN
                                  ARQFactory]
           [org.topbraid.spin.model SPINFactory
                                    Command]
           [org.apache.jena.rdf.model Model ModelFactory]
           [org.apache.jena.query Query QueryExecutionFactory]
           ))



;create jena query from sparql string
(defn ^Query string->arq
  "Create jena arq query from sparql string."
  ([^String query]
   (let [model (ModelFactory/createDefaultModel)]
     (.createQuery
       (ARQFactory/get) model query)))
  ([^Model model ^String query]
   (.createQuery
     (ARQFactory/get) model query)))


;return spin query object and model from query string
(defn string->spin
  "Return spin query object and model from query string."
  [^String query ^String query-uri]
  (let [model (ModelFactory/createDefaultModel)
        arq-query (string->arq model query)]
    {:spin-query (.createQuery
                   (ARQ2SPIN. model) arq-query query-uri)
     :spin-model model}))

(defn get-spin-model
  [^String query ^String query-uri]
  (:spin-model (string->spin query query-uri)))


(defn serialize-spin
  ([^String query
    ^String query-uri
    ^String format]
   (->(get-spin-model query query-uri)
      (serialize-model format)))
  ([^String query
    ^String query-uri
    ^String format
    ^java.io.OutputStream out]
   (->(get-spin-model query query-uri)
      (serialize-model format out))))



(defn execute-query
  [^String query ^Model model]
  (let [^Query arq-query (string->arq query)
        query-execution (query-exec model arq-query)]
    (->CloseableResultSet query-execution
                      (query* query-execution))))




