package example.micronaut.gorm.exceptions

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.HttpStatus
@Controller
class ExceptionHandler {


    @Error(global = true, exception = UserAllReadyExistException.class)
    HttpResponse<ErrorResponse> handleLoginUser(UserAllReadyExistException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.code, "this user was existed", ex.message)
        return HttpResponse.status(HttpStatus.NOT_FOUND).body(errorResponse) as HttpResponse<ErrorResponse>
    }

    @Error(global = true, exception = InvalidCredentialsException.class)
    HttpResponse<ErrorResponse> handleLoginUser(InvalidCredentialsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.code, "Not Found ", ex.message)
        return HttpResponse.status(HttpStatus.NOT_FOUND).body(errorResponse) as HttpResponse<ErrorResponse>
    }

    @Error(global = true, exception = UserNotFound.class)
    HttpResponse<ErrorResponse> handleLoginUser(UserNotFound ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.code, "Not Found ", ex.message)
        return HttpResponse.status(HttpStatus.NOT_FOUND).body(errorResponse) as HttpResponse<ErrorResponse>
    }
}
