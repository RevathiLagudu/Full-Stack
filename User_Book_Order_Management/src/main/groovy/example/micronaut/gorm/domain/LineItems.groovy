package example.micronaut.gorm.domain

import grails.gorm.annotation.Entity


@Entity
class LineItems {
    static belongsTo = [order:OrderDomain,book:BooksDomain]

}
