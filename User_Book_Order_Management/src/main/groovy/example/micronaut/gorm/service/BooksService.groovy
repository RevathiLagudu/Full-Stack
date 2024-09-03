package example.micronaut.gorm.service

import example.micronaut.gorm.domain.BooksDomain
import example.micronaut.gorm.exceptions.UserAllReadyExistException
import example.micronaut.gorm.model.BooksModel
import grails.gorm.transactions.Transactional

import javax.inject.Singleton

@Singleton
class BooksService {

    @Transactional
    def saveBooks(BooksModel booksModel){
        BooksDomain book=BooksModel.toBooks(booksModel)
        try{
            book.save()
            return book
        }
        catch (Exception){
            throw new UserAllReadyExistException("data Already exist")
        }
    }


    @Transactional
    def fetchAllBooks(){
        def users=BooksDomain.findAll()
        return users
    }
}
