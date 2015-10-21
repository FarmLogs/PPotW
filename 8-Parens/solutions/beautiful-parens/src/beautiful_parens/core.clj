(ns beautiful-parens.core
  (:gen-class))

(def +paredit+ "If paredit is not for you, then you need to become the sort of person that paredit is for. -Phil Hagelberg")

(defn acc-parens
  [acc c]
  (cond
   (#{\( \{ \[} c) (when-not (:quote acc)
                     (assoc acc c (cons c (get acc c))))
   (#{\) \} \]} c) (when-not (:quote acc)
                     (let [offset (if (= c \)) 1 2) ;; Stupid ascii. Why wouldn't open and close always be one apart?
                           opener (char (- (int c) offset))
                           dat-stack-yo (first (get acc opener))]
                       (if-not dat-stack-yo
                         (do
                           (println +paredit+)
                           (println "Unbalanced" c)
                           (assoc acc :bal false))
                         (assoc acc opener (rest (get acc opener))))))
   (= \' c) (update-in acc [:quote] not)
   :else acc))

(defn beautiful-parens?
  "Are your parens beautiful?"
  [s]
  (let [x (reduce acc-parens {\( '()
                              \[ '()
                              \{ '()
                              :quote false
                              :bal true}
                  s)]
    (and (empty? (x \())
         (empty? (x \[))
         (empty? (x \{))
         (:bal x)
         (not (:quote x)))))

(defn -main
  "lein run \"(())()()()((([[[]]]]))\""
  [paren-string & args]
  (if paren-string
    (beautiful-parens? paren-string)
    (println "Need some input...")))
