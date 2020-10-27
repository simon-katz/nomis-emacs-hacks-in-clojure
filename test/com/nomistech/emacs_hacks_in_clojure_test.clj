(ns com.nomistech.emacs-hacks-in-clojure-test
  (:require [midje.sweet :refer :all]
            [com.nomistech.emacs-hacks-in-clojure :refer :all]))

(fact
  "`should-chop-string-at-indexes` tests"
  (let [chop-string-at-indexes
        #'com.nomistech.emacs-hacks-in-clojure/chop-string-at-indexes]
    (fact (chop-string-at-indexes "1234567890"
                                  [3 5])
      => ["123" "45" "67890"])))

(fact
  "`should-rearrange-string-into-lines` tests"
  (fact (rearrange-string-into-lines
         "I think I've got to grips with it now, and I've summarised my
         understanding of the differences between
         symbols in Common Lisp and Clojure below."
         11
         80)
    => "I think I've got to grips with it now, and I've summarised my
           understanding of the differences between symbols in Common Lisp and
           Clojure below.")
  (fact (rearrange-string-into-lines
         "123456789012345 7890"
         11
         21)
    => "123456789012345
           7890")
  (fact (rearrange-string-into-lines
         "1234567890"
         11
         21)
    => "1234567890")
  (fact (rearrange-string-into-lines
         "1234567890 1"
         11
         21)
    => "1234567890
           1")
  (fact (rearrange-string-into-lines
         "12345678 0 1"
         11
         21)
    => "12345678 0
           1")
  (fact (rearrange-string-into-lines
         "12 4 6 8 0 1 2"
         11
         21)
    => "12 4 6 8 0
           1 2")
  (fact (rearrange-string-into-lines
         "1234567890 "
         11
         21)
    => "1234567890
           ")
  (fact (rearrange-string-into-lines
         "  345  "
         11
         21)
    => " 345 ")
  (fact (rearrange-string-into-lines
         "12 45 78 0
         12 45 7 90"
         11
         21)
    => "12 45 78 0
           12 45 7 90")
  (fact (rearrange-string-into-lines
         "1234567890
         123456789 1"
         11
         21)
    => "1234567890
           123456789
           1")
  (fact (rearrange-string-into-lines
         "1234567890
123456789"
         11
         80)
    => "1234567890 123456789")
  (fact (rearrange-string-into-lines
         "1



2"
         11
         80)
    => "1 2")
  (fact (rearrange-string-into-lines
         "1 1234567890"
         11
         15)
    => "1
           1234567890")
  (fact (rearrange-string-into-lines
         "1234567890"
         11
         15)
    => "1234567890"))
