package example.micronaut.gorm.model

import example.micronaut.gorm.domain.UserDomain

class UserModel {
    String name
    String email
    String password
    String phoneNumber
    String address

    static UserDomain toUser(UserModel userModel){
        if (userModel==null){
            return null
        }
        UserDomain users=new UserDomain()
        users.name=userModel.name
        users.email=userModel.email
        users.password=userModel.password
        users.phoneNumber=userModel.phoneNumber
        users.address=userModel.address
        return users
    }
}
