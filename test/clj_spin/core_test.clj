(ns clj-spin.core-test
  (:require [clojure.test :refer :all]
            [clj-spin.core :refer :all]
            [clj-spin.query :refer [execute-query]]
            [yesparql.sparql :refer [->result
                                     result->json
                                     result->tsv]]
            [clojure.string :refer [split]])

  (:use clj-spin.query
        clj-spin.utils))


;(defn write-csv-file
;  "Writes a csv file using a key and an s-o-s (sequence of sequences)"
;  [out-sos out-file]
;
;  (spit out-file "" :append false)
;  (with-open [out-data (io/writer out-file)]
;    (csv/write-csv out-data out-sos)))



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

