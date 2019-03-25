# Cleaning existing instances.
# Find IDs of processes running on the specified ports.
lsof -t -i :XXXX
kill -9 {pid}

# # This script doesn't seem to work.
# ps ax | grep -i 'kafka\.Kafka' | grep java | grep -v grep | awk '{print $1}' | xargs kill -SIGTERM

# Start the cluster manager.
bin/zookeeper-server-start.sh config/zookeeper.properties

# # NOTE:  PROPERTIES I USED TO GET IT WORKING...
# broker.id=1
# listeners=PLAINTEXT://localhost:9093
# log.dirs=/tmp/kafka-logs-1
# # The 'book' indicates no need to specify 'localhost' in the
# # listeners but that's what made it work for me.
# # https://stackoverflow.com/a/44691693/2668275

#Start the servers.
bin/kafka-server-start.sh config/server.properties 
bin/kafka-server-start.sh config/server-2.properties 

# Create a topic with two partitions.
bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic stardata --partitions 2 --replication-factor 2

# List the topic details to ensure creation.
bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topic stardata

# Create a producer and a consumer.
bin/kafka-console-producer.sh --broker-list localhost:9093 --topic stardata

bin/kafka-console-consumer.sh --bootstrap-server localhost:9093 --topic stardata
# OR 
bin/kafka-console-consumer.sh --bootstrap-server localhost:9093 --topic stardata --from-beginning
