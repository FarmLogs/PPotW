
(defn maybe-mod [divisor value]
  (if (string? value)
    value
    (mod value divisor)))

(defn capitalize [[letter & rest]]
  (as-> (str letter) *
        (.toUpperCase) 
        (cons * rest)
        (apply str *)))

(defmacro def-fizzbuzz [name divisor]
  (let [string (capitalize (str name))]
    `(do (defmulti ~name (partial maybe-mod ~divisor))
         (defmethod ~name 0 [_#] ~string)
         (defmethod ~name :default [value#] value#))))

(def-fizzbuzz fizz 3)
(def-fizzbuzz buzz 5)
(def-fizzbuzz fizzBuzz 15)

(def fizzbuzz (comp buzz fizz fizzBuzz))

(println (map fizzbuzz (range 1 101)))
