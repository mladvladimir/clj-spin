(ns clj-spin.core-test
  (:require [clojure.test :refer :all]
            [clj-spin.core :refer :all]
            [clj-spin.query :refer [execute-query]]
            [yesparql.sparql :refer [->result
                                     result->json
                                     result->tsv]]
            [clojure.string :refer [split]])

  (:use clj-spin.query
        clj-spin.utils
        clj-spin.constraints))




(def select-query (slurp "./test/clj_spin/samples/select.sparql"))
(def spin-function-query (slurp "./test/clj_spin/samples/spin_function_query.sparql"))
(def spinsquare (load-model-with-imports "./test/clj_spin/samples/spinsquare.ttl" "TTL"))

(deftest query-conversion
  (testing "Conversion failed!"
    (is (isomorphic?
          (file->model "./test/clj_spin/samples/converted.ttl")
          (get-spin-model select-query "http://example-query.com")))))

(deftest spin-functions
  (testing "Calling SPIN function failed!"
    (is (= (do
             (init-spin-registry)
             (register-all spinsquare)
             (->(execute-query spin-function-query spinsquare)
                (->result)
                (result->tsv)
                (split #"\s")
                (last)))
           "42"))))

(deftest spin-constraints
  (testing "Check SPIN Constraints failed!"
    (is (= (do
             (init-spin-registry)
             (register-all spinsquare)
             (map #(str (get-label (get-root %)) " - " (get-message %)) (check-constraints spinsquare)))
           '["ss:InvalidSquare - Width and height of a Square must be equal"]))))
