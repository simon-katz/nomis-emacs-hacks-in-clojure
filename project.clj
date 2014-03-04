(defproject com.nomistech/emacs-hacks-in-clojure "0.1.0-SNAPSHOT"
  :description "Nomistech utilities in Clojure for use in Emacs across a REPL connection."
  :url "None"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [com.nomistech/clojure-utils "0.1.0-SNAPSHOT"]]
  :profiles
  {:dev {:source-paths ["dev"]
         :dependencies [[midje "1.6.2"]]
         :plugins [[lein-midje "3.1.1"]]}})
