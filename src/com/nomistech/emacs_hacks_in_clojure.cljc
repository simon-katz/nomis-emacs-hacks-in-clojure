(ns com.nomistech.emacs-hacks-in-clojure
  (:require
   [clojure.string :as str]
   [com.nomistech.clj-utils :as nu]))

(defn ^:private chop-string-at-indexes [s indexes]
  (map (fn [start end] (subs s start end))
       (cons 0 indexes)
       (concat indexes [(count s)])))

(defn ^:private indexes-to-split-at [string line-length]
  ;; Terminology:
  ;; - "index" means index in `string'
  ;; - "offset" is relative to the start of the line that is currently being
  ;;   considered
  (let [string-length (count string)]
    (loop [current-index 0
           indexes-sofar []]
      (if (>= current-index (- string-length line-length))
        indexes-sofar
        (let [offset-of-next-space
              (nu/position #{\space} (subs string current-index))

              offset-for-newline
              (cond (nil? offset-of-next-space) ; last line?
                    nil

                    (> offset-of-next-space line-length) ; long line?
                    offset-of-next-space

                    :else
                    (nu/last-index-of-char-in-string
                     \space
                     (subs string
                           current-index
                           (inc    ; to find a space after line-length
                            (+ current-index line-length)))))]
          (if (nil? offset-for-newline)
            indexes-sofar
            (let [index (+ offset-for-newline current-index)]
              (recur (+ index 1)
                     (conj indexes-sofar index)))))))))

(defn rearrange-string-into-lines [string left-margin right-margin]
  (let [single-line (str/replace string #"[\n ]+" " ")
        newline-and-left-padding (apply str
                                        (concat "\n"
                                                (repeat (dec left-margin) " ")))
        line-length (- right-margin left-margin)
        chopped-string (chop-string-at-indexes
                        single-line
                        (indexes-to-split-at single-line line-length))
        n-chopped-strings (count chopped-string)]
    (apply str
           (interleave chopped-string
                       (concat (repeat (dec n-chopped-strings)
                                       newline-and-left-padding)
                               [""])))))
