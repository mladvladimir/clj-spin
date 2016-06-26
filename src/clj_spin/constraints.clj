(ns clj-spin.constraints
  (:import [org.topbraid.spin.constraints ConstraintViolation
                                          SPINConstraints]
           [org.topbraid.spin.model.impl ConstructImpl]

           [org.apache.jena.rdf.model Model]
           [org.apache.jena.rdf.model.impl ResourceImpl]
           [org.apache.jena.ontology OntModel]))

(defn check-constraints
  [^Model model]
  (SPINConstraints/check model nil))


(defn ^ResourceImpl get-root
  "Get root resource of violation."
  [^ConstraintViolation cv]
  (.getRoot cv))


(defn ^String get-message
  "Get message explaining the error."
  [^ConstraintViolation cv]
  (.getMessage cv))



(defn ^ConstructImpl get-source
  [^ConstraintViolation cv]
  (.getSource  cv))





