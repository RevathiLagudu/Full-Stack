package com.example.controller

import com.example.domain.User
import com.example.model.UserRequest
import com.example.service.MessageProducer
import com.example.service.UserService
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.Status
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import jakarta.inject.Inject

@Controller("/users")
class UserController {
    @Inject
    UserService userService
    @Inject
    MessageProducer messageProducer

    @Inject
    @Client("http://localhost:9090")
    HttpClient httpClient
//@ExecuteOn(TaskExecutors.BLOCKING) :-is used to specify that a method should be executed on a dedicated thread pool designed for blocking operations.
// This is important in asynchronous environments to prevent blocking operations from interfering with the non-blocking event loop or worker threads.
    @ExecuteOn(TaskExecutors.BLOCKING)
    @Post
//    HttpStatus.CREATED: Represents the HTTP status code 201, indicating that the resource was created successfully.
    @Status(HttpStatus.CREATED)
    def createUser(@Body UserRequest userRequest) {
        try {
            // Send the UserRequest to the other microservice and receive the saved user object
            HttpResponse<User> response = httpClient.toBlocking().exchange(
                    HttpRequest.POST("/user-process", userRequest),
                    User
            )

            // Check if the response is OK and contains the saved user object
            //response.body(): Retrieves the User object from the response body if it exists.
            if (response.status == HttpStatus.CREATED && response.body()) {
                User savedUser = response.body()

                // Send the saved user object via Kafka
                if (messageProducer.sendMessage(savedUser)) {
                    return HttpResponse.ok("Sent user object successfully through Kafka")
                } else {
                    return HttpResponse.serverError("Unable to send user object through Kafka")
                }
            } else {
                return HttpResponse.status(response.status).body("Failed to process user request in the other microservice")
            }
        } catch (Exception e) {
            return HttpResponse.serverError("An error occurred: ${e.message}")
        }
    }



    @ExecuteOn(TaskExecutors.BLOCKING)
    @Get
    @Status(HttpStatus.CREATED)
    def getAllUsers(){
        try{
            // Send a GET request to the other microservice to fetch all users
            HttpResponse<List<User>>response=httpClient.toBlocking().exchange(
                    HttpRequest.GET("/user-process"),
                    Argument.listOf(User)
            )
            // Check if the response is OK and contains a list of user objects
            if(response.status==HttpStatus.OK && response.body()){
                List<User>users=response.body()
                return HttpResponse.ok(users)
            }else{
                return HttpResponse.status(response.status).body("Failed to retrieve users from the other microservice")
            }
        }catch(Exception e) {
            return HttpResponse.serverError("an error occurred:${e.message}")
        }
    }

// Specifies that this method will be executed in a blocking context.
    @ExecuteOn(TaskExecutors.BLOCKING)
    @Post("/login")
    //Indicates that the response will be in JSON format.
    @Produces(MediaType.APPLICATION_JSON)
    @Status(HttpStatus.OK)
    def userLogin(@Body UserRequest userRequest){
        try {
            // Send the LoginRequest to the authentication service
            HttpResponse<UserRequest> response = httpClient.toBlocking().exchange(
                    HttpRequest.POST("/user-process/login", userRequest), User
            )
            // Check if the response is OK and contains the authentication response object
            if (response.status == HttpStatus.OK && response.body()) {
                User users = response.body()
                // Optionally, you might want to handle the authentication response here
                // For example, check if authentication was successful, handle tokens, etc.
                return HttpResponse.ok(users)
            } else {
                return HttpResponse.status(response.status).body("Failed to authenticate user ")
            }
        }catch (Exception e){
            return HttpResponse.serverError("An error occurred: ${e.message}")
        }
    }


    @ExecuteOn(TaskExecutors.BLOCKING)
    @Delete("/delete/{id}")
    @Status(HttpStatus.NO_CONTENT)
    def deleteUser(@PathVariable String id) {
        try {
            // Send a DELETE request to the other microservice to delete the user with the given ID
            HttpResponse<Void> response = httpClient.toBlocking().exchange(
                    HttpRequest.DELETE("/user-process/${id}")
            )

            // Check if the response status indicates successful deletion
            if (response.status == HttpStatus.NO_CONTENT) {
                return HttpResponse.noContent()  // 204 No Content for successful deletion
            } else {
                return HttpResponse.status(response.status).body("Failed to delete user with ID ${id}")
            }
        } catch (Exception e) {
            return HttpResponse.serverError("An error occurred: ${e.message}")
        }
    }




    @Put("/{userId}")
    def updateUser(@PathVariable int userId, @Body User user) {
        return userService.updateUser(userId, user)
    }
    @Get("/{userId}")
    def getEmail(@PathVariable int userId){
        return userService.getEmail(userId)
    }
}
