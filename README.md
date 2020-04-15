# devonKafkaApplication
A simple kafka application using [devon4j's kafka module](https://github.com/devonfw/devon4j/blob/develop/documentation/guide-kafka.asciidoc).

# sample-application

This application shows how devon4j-kafka works with the test method `saveAndDeleteEmployeeViaKafkaService`.
This test menthod has 2 checks:
* sends an employee information to kafka using MessageSender of devon4j-kafka and consuming it with the Listener service classes implemented as part of this application and saves it in Db(using MessageProcessor). Its validated using another service findEmployeeByCriteria. 
* Send an employeeId information to kakfa using MessageSender of devon4j-kafka and consuming it with the listener service classes implemented as part of this application and deletes the employee corresponding to the employeeId consumed(using MessageProcessor). 
Its validated using another service findEmployeeByCriteria

# Execution
* In order to run the test method in the sample application its required to have ZooKeeper & kafka running on your system.

```
git clone https://github.com/wurstmeister/kafka-docker.git
cd kafka-docker
docker-compose up -d
```

* Update the port number in the application.properties `messaging.kafka.common.bootstrapServers=localhost:9092`. For docker use `docker ps` to find out the mapped port number for the kafka container.
