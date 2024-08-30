package example.micronaut.gorm.domain

import grails.gorm.annotation.Entity


@Entity
class OrderDomain {
    static belongsTo = [user:UserDomain]
    String orderDate
    static hasMany = [lineItems: LineItems]




}
