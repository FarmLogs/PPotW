(ns beautiful-parens.core-test
  (:require [clojure.test :refer :all]
            [beautiful-parens.core :refer :all]))

(deftest test-beautiful-parens?
  (are [input expected] (= expected (beautiful-parens? input))
       "()()()([''][])" true
       "(((()()'')))" true
       "((((()()')')))" false
       ")))(((" false))
