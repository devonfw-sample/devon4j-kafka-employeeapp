# devonKafkaApplication
A simple kafka application using devon frameworks' kafka module.

# sample-application

This application shows how devon-kafka works with the test method saveAndDeleteEmployeeViaKafkaService.
This test menthod has 2 checks:
* sends an employee information to the kafka using MessageSender of devon-kafka and consuming it with the Listener service classes implemented as part of this application and saves it in Db(using MessageProcessor). Its validated using another service findEmployeeByCriteria. 
* Send an employeeId information to the kakfa using MessageSender of devon-kafka and consuming it with the listener service classes implemented as part of this application and deletes the employee corresponding to the employeeId consumed(using MessageProcessor). 
Its validated using another service findEmployeeByCriteria

# Execution
* In order to run the test method in the sample application its required to run the ZooKeeper & kafka installed in your system.

```
git clone https://github.com/wurstmeister/kafka-docker.git
cd kafka-docker
docker-compose up -d
```

* Update the port number in the application.properties `messaging.kafka.common.bootstrapServers=localhost:9092`.
Here it is localhost:9092, if you are using docker or anyother port number it is required to update here . 
