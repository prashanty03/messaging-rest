# messaging-rest  
Spring Boot Security with H2 database, REST endpoints to post message, follow users, unfollow users, view timeline of messages, most popular follower 

## How to Use:  
Prereq : Java 1.7+ , Gradle 2+  
git clone https://github.com/prashanty03/messaging-rest.git  
cd messaging-rest  
gradle clean build  
jar java -jar build/libs/challenge-0.0.1-SNAPSHOT.jar  

## How to Test:
Easiest way is use POSTMAN or Browser or curl etc.
1. POSTMAN  
  > * Authorization - User Type=Basic Auth, Username=batman, Password=batman and click on update request this will add a token in the header.(All the dummy username and password are present in the data.sql)
  > * GET REQUEST, Enter the URL eg. http://localhost:9000/followers and press enter  
  > * POST REQUEST, Enter the URL eg. http://localhost:9000/friends, In body put the request params eg. {"id" : 3} and press enter  
  * All request should have authorization  
  
2. Browser
  > * Enter the URL http://localhost:9000/ , the browser will ask for username and password, enter Username=batman, Password=batman and click on update request this will add a token in the header.(All the dummy username and password are present in the data.sql)  
  > * You can only test the GET Request after you have logged-in
  

# REST End points
All the end points to work, user has to be authenticated using simple authentication. So make sure you enter username and password before accessing any of the URL

> * Get all the followers for currently logged-in user GET http://localhost:9000/followers  
> * Get all the people whom currently logged-in user is following GET http://localhost:9000/friends  
> * Get the timeline for the currently logged-in user  GET http://localhost:9000/timeline  
> * Filter the timeline for the currently logged-in user GET http://localhost:9000/timeline?search=keyword  
> * Find the List of all users with their most popular follower. GET http://localhost:9000/popularfollower  
> * Follow a user with id=3, POST body= {"id":3} http://localhost:9000/friends [returns the updated list of /friends]  
> * Unfollow a user with id=3, DELETE body= {"id":3} http://localhost:9000/friends [returns the updated list of /friends]  
> * Post a message, POST bosy= {"content" : "message"} http://localhost:9000/message [return a string message]  




METHOD    |REQUEST                               |PARAMS                 | RESPONSE                                      |
----------|--------------------------------------|-----------------------|------------------------------------------------
GET | ALL MY FOLLOWERS(People following you)  http://localhost:9000/followers | NONE                      | [{"id":10,"handle":"profx","name":"Charles Xavier"},{"id":8,"handle":"spiderman","name":"Peter Parker"},{"id":5,"handle":"alfred","name":"Alfred Pennyworth"},{"id":3,"handle":"catwoman","name":"Selina Kyle"},{"id":6,"handle":"dococ","name":"Otto Octavius"},{"id":9,"handle":"ironman","name":"Tony Stark"}] |  
GET | ALL MY FRIENDS(people you are following)  http://localhost:9000/friends | NONE | [{"id":8,"handle":"spiderman","name":"Peter Parker"},{"id":10,"handle":"profx","name":"Charles Xavier"},{"id":5,"handle":"alfred","name":"Alfred Pennyworth"},{"id":4,"handle":"daredevil","name":"Matt Murdock"},{"id":2,"handle":"superman","name":"Clark Kent"}]|
GET | MY TIMELINE (message posted by me and people whom I am followin)  http://localhost:9000/timeline | NONE |[{"content":"Cras convallis convallis dolor. Quisque tincidunt pede ac urna. Ut","user":{"id":2,"handle":"superman","name":"Clark Kent"}},{"content":"sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus","user":{"id":1,"handle":"batman","name":"Bruce Wayne"}},{"content":"cubilia Curae; Donec tincidunt. Donec vitae erat vel pede blandit","user":{"id":5,"handle":"alfred","name":"Alfred Pennyworth"}},{"content":"libero. Proin sed turpis nec mauris blandit mattis. Cras eget","user":{"id":10,"handle":"profx","name":"Charles Xavier"}}] |
GET | FILTER MY TIMELINE (search messages by keyword) http://localhost:9000/timeline?search=something | NONE | [{"content":"Some men aren't looking for something logical, some men just want to watch the world burn.","user":{"id":5,"handle":"alfred","name":"Alfred Pennyworth"}}]|
GET | POPULAR FOLLOWERS FOR ALL USERS http://localhost:9000/popularfollower | NONE | [{"id":1,"handle":"batman","name":"Bruce Wayne","followerId":6,"followerHandle":"dococ","followerName":"Otto Octavius"},{"id":2,"handle":"superman","name":"Clark Kent","followerId":7,"followerHandle":"zod","followerName":"Dru-Zod"},{"id":3,"handle":"catwoman","name":"Selina Kyle","followerId":7,"followerHandle":"zod","followerName":"Dru-Zod"},{"id":4,"handle":"daredevil","name":"Matt Murdock","followerId":7,"followerHandle":"zod","followerName":"Dru-Zod"},{"id":5,"handle":"alfred","name":"Alfred Pennyworth","followerId":7,"followerHandle":"zod","followerName":"Dru-Zod"},{"id":6,"handle":"dococ","name":"Otto Octavius","followerId":7,"followerHandle":"zod","followerName":"Dru-Zod"},{"id":7,"handle":"zod","name":"Dru-Zod","followerId":6,"followerHandle":"dococ","followerName":"Otto Octavius"},{"id":8,"handle":"spiderman","name":"Peter Parker","followerId":6,"followerHandle":"dococ","followerName":"Otto Octavius"},{"id":9,"handle":"ironman","name":"Tony Stark","followerId":7,"followerHandle":"zod","followerName":"Dru-Zod"},{"id":10,"handle":"profx","name":"Charles Xavier","followerId":6,"followerHandle":"dococ","followerName":"Otto Octavius"}]|
POST | FOLLOW A USER (userId=3) http://localhost:9000/friends | {"id" : 3} | Updated List of Users(includes the new entry)[{"id":8,"handle":"spiderman","name":"Peter Parker"},{"id":10,"handle":"profx","name":"Charles Xavier"},{"id":5,"handle":"alfred","name":"Alfred Pennyworth"},{"id":4,"handle":"daredevil","name":"Matt Murdock"},{"id":2,"handle":"superman","name":"Clark Kent"},{"id":3,"handle":"catwoman","name":"Selina Kyle"}] |
DELETE | UNFOLLOW A USER (userId=3) http://localhost:9000/friend | {"id" : 3} | Updated List of Users(removes the userId passed) [{"id":8,"handle":"spiderman","name":"Peter Parker"},{"id":10,"handle":"profx","name":"Charles Xavier"},{"id":5,"handle":"alfred","name":"Alfred Pennyworth"},{"id":4,"handle":"daredevil","name":"Matt Murdock"},{"id":2,"handle":"superman","name":"Clark Kent"}]|
POST | POST A MESSAGE TO TIMELINE http://localhost:9000/message | {"content" : "here is the message"} | {"httpCode":"200","message":"Message posted to your timeline"}
