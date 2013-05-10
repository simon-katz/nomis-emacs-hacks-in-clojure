(ns com.nomistech.emacs-hacks-in-clojure-test
  (:use clojure.test
        com.nomistech.emacs-hacks-in-clojure))

(deftest should-chop-string-at-indexes
  (let [chop-string-at-indexes
        #'com.nomistech.emacs-hacks-in-clojure/chop-string-at-indexes]
    (is (= (chop-string-at-indexes "1234567890"
                                   [3 5])
           ["123" "45" "67890"]))))

(deftest should-rearrange-string-into-lines
  (is (= (rearrange-string-into-lines
          "I think I've got to grips with it now, and I've summarised my
           understanding of the differences between
           symbols in Common Lisp and Clojure below."
          10
          80)
         "I think I've got to grips with it now, and I've summarised my
          understanding of the differences between symbols in Common Lisp and
          Clojure below."))
  (is (= (rearrange-string-into-lines
          "123456789012345 7890"
          10
          20)
         "123456789012345
          7890"))
  (is (= (rearrange-string-into-lines
          "1234567890"
          10
          20)
         "1234567890"))
  (is (= (rearrange-string-into-lines
          "1234567890 1"
          10
          20)
         "1234567890
          1"))
  (is (= (rearrange-string-into-lines
          "12345678 0 1"
          10
          20)
         "12345678 0
          1"))
  (is (= (rearrange-string-into-lines
          "12 4 6 8 0 1 2"
          10
          20)
         "12 4 6 8 0
          1 2"))
  (is (= (rearrange-string-into-lines
          "1234567890 "
          10
          20)
         "1234567890
          "))
  (is (= (rearrange-string-into-lines
          "  345  "
          10
          20)
         " 345 "))
  (is (= (rearrange-string-into-lines
          "12 45 78 0
           12 45 7 90"
          10
          20)
         "12 45 78 0
          12 45 7 90"))
  (is (= (rearrange-string-into-lines
          "1234567890
           123456789 1"
          10
          20)
         "1234567890
          123456789
          1"))
  (is (= (rearrange-string-into-lines
          "1234567890
123456789"
          10
          80)
         "1234567890 123456789"))
  (is (= (rearrange-string-into-lines
          "1



2"
          10
          80)
         "1 2")))
