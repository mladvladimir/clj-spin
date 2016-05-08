(ns clj-spin.utils
  (:require [yesparql.sparql :refer [model->ttl]])
  (:import [org.apache.jena.ontology OntModel OntModelSpec]
           [org.apache.jena.rdf.model Model
                                      ModelFactory
                                      Resource
                                      Property]

           [org.topbraid.spin.model Command]
           [org.topbraid.spin.util JenaUtil
                                   SPINQueryFinder
                                   QueryWrapper]
           [org.topbraid.spin.arq ARQFactory]
           [org.topbraid.spin.vocabulary SPIN
                                         SP]))


;create jena deafult model
(defn ^Model create-default-model
  "Create jena deafult model."
  []
  (ModelFactory/createDefaultModel))


(defn ^OntModel create-ontology-model
  "Create OntModel with imports."
  [^Model model]
  (JenaUtil/createOntologyModel OntModelSpec/OWL_MEM model))


;Create OntModel with imports
(defn load-model-with-imports
  "Create OntModel with imports\n"
  [^String url ^String format]
  (let [^Model base-model (create-default-model)]
    (.read base-model url format)
    (create-ontology-model base-model)))


(defn get-class-to-query-map
  "Gets a Map of QueryWrappers with their associated classes. Wrapper around getClass2QueryMap()"
  [^Model model
   ^Model query-model
   ^Property predicate
   ^Boolean with-class
   ^Boolean allow-ask]
  (SPINQueryFinder/getClass2QueryMap model
                                     query-model
                                     predicate
                                     with-class
                                     allow-ask))


(defn ^String spin-command->sparql
  [^Command spin-query]
  (.createCommandString
    (ARQFactory/get) spin-query))


(defn map->items
  [[^Resource k ^QueryWrapper v]]
  {(.getURI k)
   (map #(spin-command->sparql (.getSPINCommand %)) v)})


(defn get-expression-map
  [^Model model ^Property predicate]
  (into {}
        (mapcat map->items
                (get-class-to-query-map model model predicate true true))))


(defn get-rules
  [^Model model]
  (get-expression-map model SPIN/rule))


;;model.listStatements(null, RDF.type, SP.Select);
;(defn get-functions
;  [^Model model])


(defn file->model
  [^String file]
  (let [model (create-default-model)]
    (.read model file)))


;(defn model->file
;  [^Model model]
;  (spit "inferred" (model->ttl model)))


