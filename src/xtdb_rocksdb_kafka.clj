(ns xtdb-rocksdb-kafka
  (:require
    [clojure.java.io :as io]
    [xtdb.api :as xt]))

(defn ^xtdb.api.IXtdb start-node []
  (xt/start-node
   {:xtdb.kafka/kafka-config {:bootstrap-servers "localhost:9092"}
    :xtdb/tx-log {:xtdb/module 'xtdb.kafka/->tx-log
                  :kafka-config :xtdb.kafka/kafka-config
                  :tx-topic-opts {:topic-name "tx-1" :replication-factor 1}}
    :xtdb/document-store {:xtdb/module 'xtdb.kafka/->document-store
                          :kafka-config :xtdb.kafka/kafka-config
                          :doc-topic-opts {:topic-name "doc-1" :replication-factor 1}}
    :xtdb/index-store {:kv-store
                       {:xtdb/module 'xtdb.rocksdb/->kv-store :db-dir (io/file "data/dev/index-store")}}
    :xtdb.http-server/server {:port 3000}}))

(def record
  {:xt/id 1
   :person/first-name "John"
   :person/last-name "Doe"})

(defn parse-cmd-args [node & args]
  (when (first args) "save" (xt/submit-tx node [[::xt/put record]])))

(defn -main [& args]
  (do
    (with-open [node (start-node)]
      (parse-cmd-args node *command-line-args*)
      (xt/sync node)
      (println (xt/entity (xt/db node) 1)))))
