(ns parens-instaparse.core
  (:require [instaparse.core :as insta]
            [clojure.java.io :refer [resource]]))

(def parens-parser (insta/parser (resource "parens.bnf")))

(def parse (comp not empty? (partial insta/parses parens-parser)))

(def -main (comp println parse))
