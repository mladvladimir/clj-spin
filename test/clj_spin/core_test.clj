(ns clj-spin.core-test
  (:require [clojure.test :refer :all]
            [clj-spin.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))

(def query "PREFIX ex: <http://example.org/demo#>
            SELECT ?person
            WHERE {
                ?person a ex:Person .
                ?person ex:age ?age .
                FILTER (?age > 18)}")




(deftest query-string-test)

(def query-1 "PREFIX ss: <http://topbraid.org/examples/spinsquare#>
              SELECT *
              WHERE {
                 ?rectangle a ss:Rectangle .
                 BIND (ss:computeArea(?rectangle) AS ?area) .
               }")