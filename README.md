# devonKafkaApplication
A simple kafka application using devon frameworks' kafka module.

# sample-application

This application shows how devon-kafka works with the test method saveAndDeleteEmployeeViaKafkaService.
This test menthod has 2 checks:
1.sends an employee information to the kafka using MessageSender of devon-kafka and consuming it with the Listener service classes implemented as part of this application and saves it in Db(using MessageProcessor). Its validated using another service findEmployeeByCriteria. 
2. Send an employeeId information to the kakfa using MessageSender of devon-kafka and consuming it with the listener service classes implemented as part of this application and deletes the employee corresponding to the employeeId consumed(using MessageProcessor). 
Its validated using another service findEmployeeByCriteria

# Execution
Inroder to run the test method in the sampel application its required to run the ZooKeeper & kafka installed in your system.
Update the port number in the application.properties " messaging.kafka.common.bootstrapServers=localhost:9092 ".
In my case it is localhost:9092, i fyou are using docker or anyother port number it is required to update here . 
