Brief Objectives -  

Asignment contains Two  Services  - employee-service and event-service. 

Employee service contains 4 endpoints

        a. Create an Employee
        b. Get an Employee by employee uuid 
        c. Delete an Employee by employee uuid 
        d. Update an employee having a valid id. 

In current project get an employee by employee uuid is an public API and available for every body and rest of the services are protected using oauth2 security token.

Event-service has two capabilities

	 a. Store each event fired by employee-service .
	 b. An expose REST API to see all events on an employee.

Delivered Artifacts - 

As a part of the delivery 5 main folders are there under root of the repository 

	1. kafka
	2. database
	3. oauthserver
	4. employeesrvc
	5. eventsrvc
	
Setup - Section below describe settting up entire system 

1. Kafka Server - It conats a docker compose file which pulls docker images for zookeeper and kafka . 
   		  This will bring up the  kafka and zookeeper server and will create a Topic called "employee" . Please perform
		  below steps - 
                        
    
           a. Update docker-compose.yaml - Update 18.216.28.244 ip with you machine IP
           b. Bring the Kafka and zookeeper up - Issue following commands 
      	
	           docker-compose up docker-compose.yaml
  
   	You can optionally run in detach mode using -d in the command .

2. database - Mysql is used as database server . This folder conatains a docker file and a sql script file.  Please perform
	       below steps - 	
      
        a. Create a docker bridge network by issuing following commands 

	        docker network create mysqlnet
  
        b. Build Docker Images  	: Issue following dommands from database folder . T

	   i.  docker build -t assignemntmysql .
	   ii. docker run -d  --net=mysqlnet -p 3306:3306 --name assignemntmysql -e MYSQL_ROOT_PASSWORD=takeaway assignemntmysql
				
3. oauthserver - This is a authoziation server . A simple sping authoziation server is used here . Follow the steps below to compile the code , build the image and run the server.

         a. Compile authoziation server - go to root directory i.e oauthserver directory
      
         b. Issue maven commands to package it by issuing following commands mvn clean compile package 
		
         c. Now go to Docker directory under oauthserver . It now conatins 3 artifacts - Dockerfile , env.list and newly create     	    authorizationServer.jar
	 
          d. Modify env.list - This file contains clientid and client secret , there is no need to change unless you want to  
         	 create a different clientid and client secret. It also conatins LOG_LEVEL. It is set to DEBUG . 
     		If you need you can change the LOG_LEVEL to INFO or other valid log levels .
		
		
          e. Issue following command to build the docker images . From oauthserver/Docker folder , issue command below
		
	        docker build -t oauthserver .
		
		
           f. Execute the oauth server - To run docker image of oauthserver issue coomand below 

	        docker run -d -p 8080:8080 --name oauthserver --env-file env.list oauthserver
		
		
4.  employeesrvc - Before running employee-service , kindly make sure kafka and database servers are running . Following steps are required to perform to run this service.
	
	        a. Compile and package - Go to root directory( employeesrvc directory )and isssue following command 
			
	                mvn clean compile package -Dspring.profiles.active=test 
			
	        This will perform all integration and unte test cases and build the jar file.
			
	        b. Build docker image - Go to the Docker directory under employeesrvc folder (i.e employeesrvc/Docker ) . 
	        Issue following commands to build docker image
	
	        docker build -t employeesrvc . 
	
	        c. Change env.list file - This file conatains all environment properties required to run employee-service .
			
		        i . Modify 18.216.28.244 ip with your host ip address in 
                        KAFKA_SERVER=18.216.28.244:9092 ,OAUTH_HOST=18.216.28.244 properties.
			
		ii. There is no change is required to rest of the properties unless you change MYSQL conatiner name , 
                MYSQL mapped / 	exposed  ports , employee database name , root password of MySql , topic name , 
                oauthserver mapped . exposed ports , client secret ,  client id.
			
 	        d. Run employeesrvc container - Run following command to execute the employee service
			
		
		docker run -d --net=mysqlnet --name=employeesrvc -p 8081:8080 --env-file env.list employeesrvc

5.eventsrvc  - Before running event-service , kindly make sure kafka and database servers are running . Following steps are required to perform to run this service.
	
	        a. Compile and package - Go to root directory( eventsrvc directory ) and isssue following command 
			
	                mvn clean compile package  
			
	       			
	        b. Build docker image - Go to the Docker directory under eventsrvc folder (i.e eventsrvc/Docker ) . 
	        Issue following commands to build docker image
	
	        docker build -t c . 
	
	        c. Change env.list file - This file conatains all environment properties required to run event-service .
			
		        i . Modify 18.216.28.244 ip with your host ip address in KAFKA_SERVER=18.216.28.244:9092 
			
		ii. There is no change is required to rest of the properties unless change any of those in while creating kafka
		or mysql.
			
 	        d. Run eventsrvc container - Run following command to execute the employee service
			
		
		docker run -d --net=mysqlnet --name=eventsrvc -p 8082:8080 --env-file env.list eventsrvc

	


Testing Steps -

In order to Test secured service you first need to get the token . In order to get the token kindly use post-man / Chrome ARC / curl . To get token you need to call followings

	A . GET TOKEN 
		1.  ouath token get URL -   http://<<oauth-server-host-ip>:8080/oauth/token?grant_type=client_credentials 
		
		2.  Use Method POST
		
		3.  Add  header 
			
			i . Basic Authorzation is used . For that create base64 encoding of clientid:clientsecret 
			as example if clientid is takeawayapi and clientsecret is takeawaysecret then Authorization header will
			be - Basic dGFrZWF3YXlhcGk6dGFrZWF3YXlzZWNyZXQ=
		
		4. Once request is successful take value of access_token key from resonse.
		

	B. Call Secure APIs
		
		1. Once you get access_token in all subsequesnt secured call will take Bearer Token as Authorization Value.
		
			As example, for post / put / delete method will take Bearer <Token_value> as Authorization Header.
			
Sample Request Information  for Testing - 

a. Create Employee

	URL : http://<employee-service-host>:8081/secured/employee
	Method : POST
	Body :	  {
  			"uuid": 0,
  			"email": "testsample123@yahoo.com",
			  "firstName": "FNAME",
			  "lastName": "LNAME",
			  "dateOfBirth": null
		   }

b. Get Employee :
		
	URL:	http://<employee-service-host>:8081/secured/employee/3
	Method:	GET

c. Update Employe	
		
	URL: http://<employee-service-host>:8081/secured/employee/3
	Method: PUT 
	{
	  "uuid": 3,
	  "email": "modified@yahoo.com",
	  "firstName": "FNAME",
	  "lastName": "LNAME",
	  "dateOfBirth": null
	}

d. Delete Employee 

	URL:http://<employee-service-host>:8081/secured/employee/3
	Method: DELETE
	
API Documentation 

Swagger UI for Employee Service is available in http://<employee-service-host>:8081/swagger-ui.html
	
Swagger File for Employee Service is available in http://<employee-service-host>:8081/v2/api-docs







