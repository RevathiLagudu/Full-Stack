package example.micronaut.gorm.model


import example.micronaut.gorm.domain.BooksDomain


class BooksModel {

    String title
    Integer price
    String pubdate

    static BooksDomain toBooks(BooksModel booksModel){
        if (BooksModel==null){
            return null
        }
        BooksDomain booksDomain=new BooksDomain()
        booksDomain.title=booksModel.title
        booksDomain.price=booksModel.price
        booksDomain.pubdate=booksModel.pubdate

        return booksDomain
    }



}
