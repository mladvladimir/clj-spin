(ns clj-spin.models
  (:import [org.apache.jena.ontology OntModel OntModelSpec]
           [org.apache.jena.rdf.model Model
                                      ModelFactory]
           [org.topbraid.spin.util JenaUtil]))

;create jena deafult model
(defn ^Model create-default-model
  "Create jena deafult model."
  []
  (ModelFactory/createDefaultModel))

;Create OntModel with imports
(defn ^OntModel create-ontology-model
  "Create OntModel with imports."
  [^Model model]
  (JenaUtil/createOntologyModel OntModelSpec/OWL_MEM model))