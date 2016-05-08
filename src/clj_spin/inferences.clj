(ns clj-spin.inferences
  (:import [org.apache.jena.rdf.model Model
                                      ModelFactory]
           [org.apache.jena.ontology OntModel
                                     OntModelSpec]
           [org.apache.jena.graph Triple]

           [org.topbraid.spin.inference SPINInferences
                                        SPINExplanations]
           [org.topbraid.spin.util JenaUtil]))




; Run all inferences
(defn run-inferences
  "Run all inferences."
  ([^OntModel ont-model ^Model new-triples]
  (SPINInferences/run ont-model new-triples nil nil false nil))
  ([^OntModel ont-model ^Model new-triples ^SPINExplanations explanations]
  (SPINInferences/run ont-model new-triples explanations nil false nil)))


(defn explain-triple
  [^Triple triple]
  (doto
    (SPINExplanations.)
    (.getText triple)))


