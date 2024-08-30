package example.micronaut.gorm.controller

import example.micronaut.gorm.model.BooksModel
import example.micronaut.gorm.service.BooksService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post

import javax.inject.Inject


@Controller("/books")
class BooksController {

    @Inject
    BooksService booksService


    @Post
    def saveBooks(@Body BooksModel booksModel){
        return booksService.saveBooks(booksModel)
    }

    @Get
    def GetAllBooks(){
        return booksService.fetchAllBooks()
    }


}
