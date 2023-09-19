docker exec --interactive --tty kafka1 kafka-console-consumer --bootstrap-server localhost:9092,kafka2:19093,kafka3:19094 --topic order-events-local --from-beginning

docker exec --interactive --tty kafka1 kafka-console-producer --bootstrap-server localhost:9092,kafka2:19093,kafka3:19094 --topic order-events-local