package com.example.controller

import com.example.domain.User
import com.example.model.UserRequest
import com.example.service.MessageProducer
import com.example.service.UserService
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
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

    @ExecuteOn(TaskExecutors.BLOCKING)
    @Post
    @Status(HttpStatus.CREATED)
    def createUser(@Body UserRequest userRequest) {
        try {
            // Send the UserRequest to the other microservice and receive the saved user object
            HttpResponse<User> response = httpClient.toBlocking().exchange(
                    HttpRequest.POST("/user-process", userRequest),
                    User
            )

            // Check if the response is OK and contains the saved user object
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

    @Delete("/{userId}")
    def removeUser(@PathVariable  int userId){
        return  userService.removeUser(userId)
    }
    @Get
    def getAllUsers(){
        return userService.getAllUsers()
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
