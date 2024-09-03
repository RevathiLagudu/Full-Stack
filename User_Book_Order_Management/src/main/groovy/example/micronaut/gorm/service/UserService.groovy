package example.micronaut.gorm.service

import example.micronaut.gorm.domain.UserDomain
import example.micronaut.gorm.exceptions.InvalidCredentialsException
import example.micronaut.gorm.exceptions.UserAllReadyExistException
import example.micronaut.gorm.exceptions.UserNotFound
import example.micronaut.gorm.model.UserModel
import grails.gorm.transactions.Transactional

import javax.inject.Singleton

@Singleton
class UserService {

    @Transactional
    def saveUser(UserModel userModel){
        UserDomain users=UserModel.toUser(userModel)
        try{
            users.save()
            return users
        }
       catch (Exception){
           throw new UserAllReadyExistException("data Already exist")
       }
    }


    @Transactional
    def fetchAllUsers(){
        def users=UserDomain.findAll()
        return users
    }

    @Transactional
    def fetchById(long id){
        UserDomain userDomain=UserDomain.findById(id)
        if(userDomain){
            return userDomain
        }
        throw new UserNotFound("this user was not exist")
    }

    @Transactional
    def userLogin(String email,String password){
        UserDomain userDomain=UserDomain.findByEmailAndPassword(email,password)
        if (userDomain) {
            return convertDomainToModel(userDomain)
        }
            throw new InvalidCredentialsException("Invalid email or password")
    }

    def convertDomainToModel(UserDomain userDomain) {
        new UserModel(
                name: userDomain.name,
                email: userDomain.email,
//                password: userDomain.password,
                phoneNumber: userDomain.phoneNumber,
                address: userDomain.address
        )
    }

    @Transactional
    def deleteUser(Long id){
        UserDomain userDomain = UserDomain.findById(id)
        if (userDomain) {
            userDomain.delete()
            return "removed successfully"
        } else {
            throw new UserNotFound("User not Found")
        }
    }

    @Transactional
    def updateUser(Long id, UserModel updatedUser){
        UserDomain users = UserDomain.findById(id)
        if (users) {
            users.name = updatedUser.name
            users.email = updatedUser.email
            users.address = updatedUser.address
            users.password=updatedUser.password
            users.phoneNumber=updatedUser.phoneNumber
            return users.save()
        } else {
            throw new UserNotFound("User Not Found")
        }
    }

  }
