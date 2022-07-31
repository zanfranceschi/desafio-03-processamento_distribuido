(ns processamento-distribuido.distribuidor
  (:require [zeromq.zmq :as zmq]
            [clojure.string :as str]
            [clojure.data.json :as json]))

;; https://antoniogarrote.wordpress.com/2010/09/08/zeromq-and-clojure-a-brief-introduction/
;; https://github.com/zeromq/cljzmq

(defn distribui!
  []
  (let [context (zmq/context)
        linhas (str/split-lines (slurp ""))]
    (with-open [publisher (doto (zmq/socket context :push)
                            (zmq/bind "tcp://*:9000"))]
      (doseq [linha linhas]
        
        (zmq/send-str publisher (json/write-str {:batch_id "1"
                                                 :batch_part 1
                                                 :total_lines 2
                                                 :lines ["1 2 345" "2 3 4"]}))))))