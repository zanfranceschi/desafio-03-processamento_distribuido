(ns processamento-distribuido.distribuidor
  (:require [zeromq.zmq :as zmq]
            [clojure.data.json :as json]
            [clojure.java.io :as io])
  (:import (java.io BufferedReader StringReader)))

(defn arquivo!
  "Generates a file with X lines and Y random numbers for each line."
  [filename linhas numeros-por-linha]
  (with-open [w (io/writer filename)]
    (doseq [_ (range 0 linhas)]
      (doseq [_ (range 0 numeros-por-linha)]
        (.write w (str (+ (rand-int 512) 1) " ")))
      (.write w "\n"))))

(defn linhas-arquivo!
  [filename]
  (line-seq (BufferedReader. (StringReader. (slurp filename)))))

(defn divisao-linhas
  ([todas-linhas buffer]
   (divisao-linhas todas-linhas buffer []))
  ([todas-linhas buffer linhas-divididas]
   (if (= (count todas-linhas) 0)
     linhas-divididas
     (let [partes (split-at buffer todas-linhas)
           novas-linhas-divididas (first partes)
           linhas-restantes (last partes)]
       (recur linhas-restantes buffer (conj linhas-divididas novas-linhas-divididas))))))

(defn distribui!
  [filename line-buffer-size linhas-arquivo numeros-por-linha-arquivo]
  (arquivo! filename linhas-arquivo numeros-por-linha-arquivo)
  (let [linhas (linhas-arquivo! filename)
        linhas-totais (count linhas)
        linhas-divididas (divisao-linhas linhas line-buffer-size)
        batch-id (.toString (java.util.UUID/randomUUID))
        context (zmq/context)]
    (with-open [publisher (doto (zmq/socket context :push)
                            (zmq/bind "tcp://*:9000"))]
      (doseq [linhas-string linhas-divididas]
        (zmq/send-str publisher (json/write-str {:batch_id batch-id
                                                 :total_lines linhas-totais
                                                 :lines linhas-string}))))
    batch-id))


(distribui! "teste" 512 50000 200)