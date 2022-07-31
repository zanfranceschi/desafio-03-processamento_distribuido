(defproject processamento-distribuido "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :repositories [["sonatype" {:url "https://oss.sonatype.org/content/repositories/snapshots"
                              :update :always}]]
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.json "2.4.0"]
                 [org.zeromq/jeromq "0.5.2"]
                 [org.zeromq/cljzmq "0.1.5-SNAPSHOT" :exclusions [org.zeromq/jzmq]]]
  :main ^:skip-aot processamento-distribuido.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
