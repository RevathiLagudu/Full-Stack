package example.micronaut.gorm.exceptions

class UserAllReadyExistException extends RuntimeException{
    UserAllReadyExistException(String message) {
        super(message)
    }
}
