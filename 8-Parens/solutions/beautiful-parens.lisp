(defpackage :beautiful-parens
  (:use "COMMON-LISP"))

(defun beautiful-parens-p (s &optional stack quote)
  (if s
    (let* ((s (if (listp s) s (concatenate 'list s)))
           (c (first s)))
      (cond
        ((member c '(#\( #\{ #\[))
         (if quote
           (beautiful-parens-p (rest s) stack quote)
           (beautiful-parens-p (rest s) (cons c stack) quote)))
        ((member c '(#\) #\} #\]))
         (if quote
           (beautiful-parens-p (rest s) stack quote)
           (let* ((f (char-code (or (first stack) #\null)))
                  (f (when (not (zerop f)) (code-char (+ f (if (= 40 f) 1 2))))))
             (when (and f (char= f c))
               (beautiful-parens-p (rest s) (rest stack) quote)))))
        ((char= c #\') (beautiful-parens-p (rest s) stack (not quote)))))
    (not stack)))
