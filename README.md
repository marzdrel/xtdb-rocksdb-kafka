# How to reproduce

```
# Use "save" to create record
% clj -M -m xtdb-rocksdb-kafka save
{:person/first-name John, :person/last-name Doe, :xt/id 1}

# Try to query the database
% clj -M -m xtdb-rocksdb-kafka
nil

# Remove RocksDB data directory and try to query the database
% rm -rf data
% clj -M -m xtdb-rocksdb-kafka
{:person/first-name John, :person/last-name Doe, :xt/id 1}

# Try to query the database
clj -M -m xtdb-rocksdb-kafka
nil

# Remove RocksDB data directory and try to query the database
% rm -rf data
% clj -M -m xtdb-rocksdb-kafka
{:person/first-name John, :person/last-name Doe, :xt/id 1}
```