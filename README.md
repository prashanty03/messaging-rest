# messaging-rest  
Spring Boot Security with H2 database, REST endpoints to post message, follow users, unfollow users, view timeline of messages, most popular follower 

## How to Use:  
Prereq : Java 1.7+ , Gradle 2+  
git clone https://github.com/prashanty03/messaging-rest.git  
cd messaging-rest  
gradle clean build  
jar java -jar build/libs/challenge-0.0.1-SNAPSHOT.jar  

## How to Test:
Easiest way is use POSTMAN or Browser or curl command  
1. POSTMAN  
  > Authorization - User Type=Basic Auth, Username=batman, Password=batman and click on update request this will add a token in the header.  
  > GET REQUEST, Enter the URL eg. http://localhost:9000/followers and press enter  
  > POST REQUEST, Enter the URL eg. http://localhost:9000/friends, In body put the request params eg. {"id" : 3} and press enter  
  * All request should have authorization 



# REST End points
All the end points to work, user has to be authenticated using simple authentication. So make sure you enter username and password before accessing any of the URL

> Get all the followers for currently logged-in user GET http://localhost:9000/followers  
> Get all the people whom currently logged-in user is following GET http://localhost:9000/friends  
> Get the timeline for the currently logged-in user  GET http://localhost:9000/timeline  
> Filter the timeline for the currently logged-in user GET http://localhost:9000/timeline?search=keyword  
> Find the List of all users with their most popular follower. GET http://localhost:9000/popularfollower  
> Follow a user with id=3, POST body= {"id":3} http://localhost:9000/friends [returns the updated list of /friends]  
> Unfollow a user with id=3, DELETE body= {"id":3} http://localhost:9000/friends [returns the updated list of /friends]  
> Post a message, POST bosy= {"content" : "message"} http://localhost:9000/message [return a string message]  


