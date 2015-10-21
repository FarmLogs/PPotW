(ns beautiful-parens.core
  (:gen-class))

(defn acc-parens
  [acc c]
  (if (:quote acc)
    (if (= \' c)
      (update-in acc [:quote] not)
      acc)
    (cond
     (#{\( \)} c) (update-in acc [:curved] inc)
     (#{\[ \]} c) (update-in acc [:square] inc)
     (#{\{ \}} c) (update-in acc [:curly] inc)
     (= \' c) (update-in acc [:quote] not)
     :else acc)))

(defn check-acc
  [[k v]]
  (if (= k :quote)
    (do
      (when v
        (println "Fix yo' syntax! Unbalanced" (name k) "bracket"))
      (not v))
    (if (even? v)
      true
      (println "Fix yo' syntax! Unbalanced" (name k) "bracket"))))

(defn beautiful-parens?
  "Are your parens beautiful?"
  [s]
  (every?
   true?
   (map
    check-acc
    (reduce
     acc-parens
     {:curved 0
      :square 0
      :curly 0
      :quote false}
     s))))

(defn -main
  "lein run \"(())()()()((([[[]]]]))\""
  [paren-string & args]
  (if paren-string
    (beautiful-parens? paren-string)
    (println "Need some input...")))
