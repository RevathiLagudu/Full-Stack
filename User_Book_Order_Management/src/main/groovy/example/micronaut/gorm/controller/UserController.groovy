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

    @Get("/{id}")
    def getById(@PathVariable long id){
        return userService.fetchById(id)
    }

    @Get
    def GetAllUsers(){
        return userService.fetchAllUsers()
    }

    @Post("/login")
    def login(@Body UserModel userModel){
        return userService.userLogin(userModel.email , userModel.password)
    }
    @Delete
    def deleteById(@PathVariable long id){
        return userService.deleteUser(id)
    }

    @Put
    def updateUser(@PathVariable long id ,@Body UserModel userModel){
        return userService.updateUser(id,userModel)
    }
}
