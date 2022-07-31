(ns processamento-distribuido.core
  (:require [processamento-distribuido.distribuidor :as distribuidor])
  (:gen-class))

;; https://antoniogarrote.wordpress.com/2010/09/08/zeromq-and-clojure-a-brief-introduction/
;; https://github.com/zeromq/cljzmq

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (distribuidor/distribui!)
  (println "Hello, World!"))
