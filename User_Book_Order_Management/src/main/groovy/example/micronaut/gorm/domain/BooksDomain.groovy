package example.micronaut.gorm.domain


import grails.gorm.annotation.Entity

@Entity
class BooksDomain {
    String title
    Integer price
    String pubdate

    static constraints = {
        title nullable: true
        price nullable: true
        pubdate nullable: true

    }

}
