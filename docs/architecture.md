## Components
- API Gateway (Spring Cloud Gateway)
- user-service (Postgres + JWT auth)
- post-service (produces `posts` events to Kafka)
- feed-service (consumes `posts` and writes timelines to Cassandra)
- search-service (consumes `posts` and indexes to Elasticsearch)
- Kafka cluster (event bus)
- Postgres / Cassandra / Elasticsearch (stores)
- platform-operator (IndexLifecycle, CassandraRepair)


## High-level flow
Client -> API Gateway -> user-service / post-service
post-service -> produces `posts` topic -> search-service (indexing)
                                       -> feed-service (timeline writes)
                                       -> notification-service (optional)
