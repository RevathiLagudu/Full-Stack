package example.micronaut.gorm.exceptions

class UserNotFound extends RuntimeException{
    UserNotFound(String message){
        super(message)
    }

}
