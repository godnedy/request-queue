## How to use ##
request-queue is an implementation of message queue. It uses rabbitmq for asynchronous publishing and receiving requests from queue.

## Creating .jar file
In order to create .jar file execute: mvn clean package

## Running application

Prerequisites:
- installed docker

In order to run application start docker container with rabbitmq server with default ports:
 
``
    docker run -d --hostname my-rabbit --name request_queue -p 15672:15672 -p 5672:5672 rabbitmq:3-management
``

 and then execute:
 
 ``
    java - jar ./target/request-queue-1.0.jar
 ``
from root directory of the project.

## Endpoints ##
 *Send request*
  ----
    Sends request to the queue
  
  * **URL**
  
    /requests
  
  * **Method:**
  
    `POST`
    
  *  **URL Params**
  
    None
  
  * **Data Params**
  
   **Required:**
 
   `type=[string], {Type1, Type2, Type3, Type4}`
   
   `message=[string]`
   
  * **Success Response:**
  
    * **Code:** 200 <br />
    
    OR 
  
    * **Code:** 202 <br />
     
     
   Example call:
   
        curl -X POST \
          http://localhost:8080/requests \
          -H 'Cache-Control: no-cache' \
          -H 'Content-Type: application/json' \
          -d '{"type":"Type1",
        	   "message": "some message"}'
    
 *Retrieve list of requests*
  ----
    Retrieves list of all requests of Type2
  
  * **URL**
  
    /requests
  
  * **Method:**
  
    `GET`
    
  *  **URL Params**
  
    None
  
  * **Data Params**
  
    None
  
  * **Success Response:**

    * **Code:** 200 <br />
      **Content:** `{ id: 1, message: "Message1", timestamp: }
                    { id: 1, message: "Message1", timestamp: }`
