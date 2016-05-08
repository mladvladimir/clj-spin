(ns clj-spin.constraints
  (:import [org.topbraid.spin.constraints ConstraintViolation
                                          SPINConstraints]
           [org.topbraid.spin.system SPINLabels]

           [org.apache.jena.rdf.model Model]
           [org.apache.jena.ontology OntModel]))

(defn check-constraints
  [^Model model]
  (SPINConstraints/check model nil))


(defn cv-messages
  "Constraint Violation messages"
  [^ConstraintViolation cv]
  (println
    (str
      (.getLabel (SPINLabels/get) (.getRoot cv))
      (.getMessage cv))))