(ns processamento-distribuido.sandbox)

(def linhas [[1 1 1] [2 2 2] [3 3 3] [4 4 4] [5 5 5] [6 6 6] [7 7 7]])

(defn divisao
  ([linhas-juntas divisao-a-cada]
   (divisao linhas-juntas divisao-a-cada []))
  ([linhas-juntas divisao-a-cada linhas-divididas]
   (if (= (count linhas-juntas) 0)
     linhas-divididas
     (let [partes (split-at divisao-a-cada linhas-juntas)
           novas-linhas-divididas (first partes)
           linhas-juntas-restantes (last partes)]
       (recur linhas-juntas-restantes divisao-a-cada (conj linhas-divididas novas-linhas-divididas))))))


(divisao linhas 3)

