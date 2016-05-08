(defproject clj-spin "0.1.0"
  :description "Clojure wrapper of TopBraid SPIN API"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories {"org.topbraid" "http://topquadrant.com/repository/spin"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.apache.jena/apache-jena-libs "3.0.1" :extension "pom"]
                 [org.topbraid/spin "2.0.0"]
                 [yesparql "0.3.0"]])
