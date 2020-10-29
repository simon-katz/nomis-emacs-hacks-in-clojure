(ns com.nomistech.emacs-hacks-in-clojure
  (:require
   [clojure.string :as str]))

(defn ^:private add-char [{:keys [lines-sofar
                                  line-sofar
                                  word-sofar
                                  line-sofar-choppable?]
                           :as sofar}
                          c
                          separator
                          max-line-length]
  ;; We use the terms "lines", "line" and "word". These are misnomers, but easy
  ;; to understand. Imagine we are dealing with strings and characters.
  (if (= separator c)
    (cond
      ;; Special case: if c is a leading separator, add it to `word-sofar`.
      (and (zero? (count lines-sofar))
           (empty? line-sofar)
           (every? #{separator} word-sofar))
      (-> sofar
          (update :word-sofar conj c))
      ;; If we have a word of length `max-line-length` or more, add it to
      ;; `lines-sofar`. (It will be at the beginning of a line, because we will
      ;; have started a new line when we previously reached `max-line-length`.)
      (>= (count word-sofar)
          max-line-length)
      (do (assert (empty? line-sofar))
          (-> sofar
              (update :lines-sofar conj word-sofar)
              (assoc  :line-sofar [])
              (assoc  :word-sofar [])
              (assoc  :line-sofar-choppable? false)))
      ;; Add word to `line-sofar`.
      :else
      (-> sofar
          (update :line-sofar concat (if (empty? line-sofar)
                                       word-sofar
                                       (concat [separator]
                                               word-sofar)))
          (assoc  :word-sofar [])
          (assoc  :line-sofar-choppable? true)))
    (if (or (not line-sofar-choppable?)
            (< (+ 1 (count line-sofar) (count word-sofar))
               max-line-length))
      ;; `line-sofar` is not choppable or we can add to `word-sofar` without
      ;; exceeding `max-line-length`, so add c to `word-sofar`.
      (-> sofar
          (update :word-sofar conj c))
      ;; We need a new line.
      (-> sofar
          (update :lines-sofar conj line-sofar)
          (assoc  :line-sofar [])
          (update :word-sofar conj c)
          (assoc  :line-sofar-choppable? false)))))

(defn chop-seq [separator s max-piece-length]
  (let [{:keys
         [lines-sofar
          line-sofar
          word-sofar]} (reduce (fn [sofar c]
                                 (add-char sofar
                                           c
                                           separator
                                           max-piece-length))
                               {:lines-sofar           []
                                :line-sofar            []
                                :word-sofar            []
                                :line-sofar-choppable? false}
                               s)]
    (concat lines-sofar
            [(if (empty? line-sofar)
               word-sofar
               (concat line-sofar [separator] word-sofar))])))

(defn rearrange-string-into-lines [string left-margin right-margin]
  (let [single-line              (str/replace string #"[\n ]+" " ")
        max-line-length          (- right-margin left-margin)
        chopped-seq              (chop-seq \space
                                           single-line
                                           max-line-length)
        n-chopped-seqs           (count chopped-seq)
        newline-and-left-padding (concat [\newline]
                                         (repeat left-margin \space))
        result-as-seqs           (interleave
                                  chopped-seq
                                  (concat (repeat (dec n-chopped-seqs)
                                                  newline-and-left-padding)
                                          [[]]))]
    (apply str
           (apply concat result-as-seqs))))
