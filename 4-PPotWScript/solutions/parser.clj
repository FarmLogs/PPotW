;;  cat example2.ppotw | clojure solutions/parser.clj

(require '[clojure.string :refer [split trim]])

(defn break
  ([] (break "Something broke"))
  ([msg] (throw (Exception. msg))))

(defn parse-op [op]
  (case op
    "*" *
    "+" +
    "-" -
    "/" /))

(defn num-or-var [vars item]
  (try
    (Float. item)
    (catch NumberFormatException e
      (get vars item))))

(defn expression [vars expr]
  (let [expr (split expr #" ")
        _ (when (> (count expr) 3) (break "Expression too long"))
        [left op right] expr]
    (if (nil? op)
      (num-or-var vars left)
      (apply (parse-op op) (map (partial num-or-var vars) [left right])))))

(defmulti interpret
  (fn [vars ^String line]
    (cond (= (seq "out") (take 3 line)) :print
          (.contains line "=") :assign
          (expression vars line) :skip)))

(defmethod interpret :assign
  [vars line]
  (let [[var-name expr] (map trim (split line #"="))]
    (if (re-matches #"[A-Za-z]+" var-name)
      (assoc vars var-name (expression vars expr))
      (break "Bad var name"))))

(defn format-num [num]
  (if (== num (int num))
    (int num)
    num))

(defmethod interpret :print
  [vars line]
  (let [expr (trim (apply str (drop 3 line)))]
    (println (format-num (expression vars expr)))
    vars))

(defmethod interpret :skip [vars _] vars)

(defmethod interpret :default [_ _] (break))

(defn -main [& _]
  (let [input (slurp *in*)]
    (reduce interpret {} (split input #"\n"))))

(-main)
