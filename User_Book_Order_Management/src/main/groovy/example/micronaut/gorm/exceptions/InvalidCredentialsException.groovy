package example.micronaut.gorm.exceptions

class InvalidCredentialsException extends RuntimeException{
    InvalidCredentialsException(String message){
        super(message)
    }

}
