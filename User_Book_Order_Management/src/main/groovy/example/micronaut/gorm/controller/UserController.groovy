package example.micronaut.gorm.controller

import example.micronaut.gorm.model.UserModel
import example.micronaut.gorm.service.UserService
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

import javax.inject.Inject

@Controller("/user-process")
class UserController {
    @Inject
    UserService userService

    @Post
    @Status(HttpStatus.CREATED)
    def saveUser(@Body UserModel userModel){
        try{
            def user=userService.saveUser(userModel)
            if(user){
                return HttpResponse.created(user)
            }else{
                return HttpResponse.badRequest("Failed to add user")
            }
        }catch (Exception e){
            return HttpResponse.serverError("An error occurred:${e.message}")
        }
    }

    @Get
    @Status(HttpStatus.CREATED)
    def GetAllUsers(){
        try{
            def users=userService.fetchAllUsers()
            if(users && !users.isEmpty()){
                return HttpResponse.ok(users)
            }else{
                return HttpResponse.noContent()//no user found
            }
        }catch (Exception e){
            return HttpResponse.serverError("An error occured:${e.message}")
        }
    }


    @Post("/login")
    @Status(HttpStatus.OK)
    def login(@Body UserModel userModel){
        try{
            // Assuming userService.authenticate returns a user object if credentials are valid
            def user=userService.userLogin(userModel.email,userModel.password)
            if(user){
                return HttpResponse.ok(user)
            }else{
                return HttpResponse.unauthorized("Invalid email or password")
            }
        }catch(Exception e){
            return HttpResponse.serverError("An error occurred:${e.message}")
        }
    }

//    @Get("/{id}")
//    def getById(@PathVariable long id){
//        return userService.fetchById(id)
//    }

    @Get("/{id}")
    @Status(HttpStatus.OK)
    def getById(@PathVariable long id) {
        try {
            def user = userService.fetchById(id)
            if (user) {
                return HttpResponse.ok(user)
            } else {
                return HttpResponse.notFound("User not found")
            }
        } catch (Exception e) {
            return HttpResponse.serverError("An error occurred: ${e.message}")
        }
    }

    @Delete("/delete/{id}")
    //@Status(HttpStatus.NO_CONTENT): Sets the default status code for the response to 204 No Content, indicating a successful deletion with no content returned.
    @Status(HttpStatus.NO_CONTENT)
    def deleteById(@PathVariable long id){
        try {
            boolean delete=userService.deleteUser(id)
            if(delete){
                return HttpResponse.noContent()// Successfully deleted

            }else{
                return HttpResponse.notFound("user not found")
            }
        }catch (Exception e){
            return HttpResponse.serverError("An error occurred: ${e.message}")
        }

    }


    @Put("/update/{userId}")
    @Status(HttpStatus.CREATED)
    def updateUser(@PathVariable Long userId, @Body UserModel updatedUserRequest) {
        try {
            def updatedUser = userService.updateUser(userId,updatedUserRequest)
            if (updatedUser) {
                return HttpResponse.created(updatedUser)
            } else {
                return HttpResponse.badRequest("Failed to update user")
            }
        } catch (Exception e) {
            return HttpResponse.serverError("An error occurred: ${e.message}")
        }
    }



}
