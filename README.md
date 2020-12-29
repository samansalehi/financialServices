# Simple Financial Services Event Sourcing App
This simple app is design to show how microservice is communicate internally and how we can leverage the Axon Framework and spring-cloud to implement microservices.
##Microservices List 
* accountservice for account handling [http://localhost:8081](http://localhost:8081)
* transactionservice for transaction handling [http://localhost:8080](http://localhost:8080)
* config-server for cloud configuration [http://localhost:8888](http://localhost:8888)
* eureka-server for discovery service [http://localhost:8761](http://localhost:8761)


## How to Run 
* Clone repository 
* Build the docker container
* Launch the containers
* Open application

### Clone the repository

```
https://github.com/samansalehi/financialServices.git
```

### Build the docker container

From the project root directory:

```
$ ./build.sh
```

### Launch the containers

From the project root directory:

```
$ docker-compose up
```

### open application
For running application 
first of all you need to point [localhost:8081/swagger-ui.html](localhost:8081/swagger-ui.html) 
to get list of all customers by /all rest service, then you can copy customerId of random user from creating new account.
Also, for getting customer's list you can use: 
 ```
curl -X GET "http://localhost:8081/all" -H  "accept: */*"
 ```
for creating new account you can use the swagger service /account/create or use the curl command:
```
curl -X POST "http://localhost:8081/account/create" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{  \"balance\": 1000000,  \"currency\": \"DOLLER\",  \"customerId\": \"1122000001\"}"
```
If the account balance for new account > 0 then a transaction command send to the transactionservice
to check that if new account is created you can point to [http://localhost:8081/h2-console/login.jsp](http://localhost:8081/h2-console/login.jsp) and check the Account Table.
Also, you can point to the [transactionservice](http://localhost:8080/) and list all the transaction by AccountId.
After new account is created then you can debit and credit account by swagger rest webservices.
when you credit or debit to the account then the transaction list in transaction service updated automatically by laverageing QuerySubscription in axon framework.
#### config-repo
config-repo is located in: 
```
https://github.com/samansalehi/config-repo.git
```
when you run the build.sh automatically clone this repo from the above url to the /home/config-rep to used by config-server.    
 
#Attention
if the config-server can not read the file from config-repo in docker container then the default port from the properties file is read.
