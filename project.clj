(defproject com.nomistech/emacs-hacks-in-clojure "0.1.0-SNAPSHOT"
  :description "Nomistech utilities in Clojure for use in Emacs across a REPL connection."
  :url "None"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [com.nomistech/clj-utils "0.14.0"]]
  :profiles
  {:dev {:source-paths ["dev"]
         :dependencies [[midje "1.9.9"]]
         :plugins [[lein-midje "3.1.1"]]}})
