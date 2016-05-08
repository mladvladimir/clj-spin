(ns clj-spin.core
  (:use clj-spin.utils
        clj-spin.query
        clj-spin.inferences)
  (:require [clojure.java.io :as io]
            [clojure.java.io :refer [output-stream]]
            [yesparql.sparql :refer [model->ttl serialize-model]])

  (:import [org.topbraid.spin.arq ARQ2SPIN ARQFactory]
           [org.topbraid.spin.system SPINModuleRegistry]


           [org.apache.jena.rdf.model Model ModelFactory]
           [org.apache.jena.query Query]
           [org.apache.jena.graph Triple]
           [org.topbraid.spin.inference SPINExplanations]))



(defn init-spin-registry
  "Initialize system functions and templates"
  []
  (doto
    (SPINModuleRegistry/get)
    (.init)))


(defn register-all
  "Register locally defined functions and templates from a given model"
  [ont-model]
  (doto
    (SPINModuleRegistry/get)
    (.registerAll ont-model
                  ;(add-sub-model
                  ;  (ontology-model (file->model url)))
                  nil)))




;// Now turn it back into a Jena Query
(defn ^Query spin->arq
  [spin-query]
  "convert spin query object to jena query object."
  (.createQuery (ARQFactory/get) spin-query))



;// Create and add Model for inferred triples
;(defn add-sub-model
;  "Create and add Model for inferred triples."
;  [ont-model]
;  (let [infered-model (create-default-model)]
;    (.addSubModel ont-model infered-model)
;    ont-model))



(defn execute
  [^String file]
  (let [ont-model (create-ontology-model(file->model file))
        new-triples (create-default-model)]
    (init-spin-registry)
    (.addSubModel ont-model new-triples)
    (register-all ont-model)
    (run-inferences ont-model new-triples)))



(defn get-resource
  [^Model model ^String s]
  (.getResource model s))

(defn get-property
  [^Model model ^String s]
  (.getProperty model s))

;explain
;SPINExplanations explain = new SPINExplanations()
;(
;  (.asTriple
;    (.getProperty
;      new-triples
;      (.getResource new-triples person)
;      (.getProperty new-triples age))))
;
;(-> (.getProperty
;      new-triples
;      (.getResource new-triples person)
;      (.getProperty new-triples age))
;    (.asTriple)
;    (.getText (SPINExplanations.) new-triples))











