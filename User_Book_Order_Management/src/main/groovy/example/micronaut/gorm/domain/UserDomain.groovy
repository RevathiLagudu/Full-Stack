package example.micronaut.gorm.domain

import grails.gorm.annotation.Entity
import groovy.transform.ToString

@Entity
@ToString(includeNames = true)
class UserDomain {
    String name
    String email
    String password
    String phoneNumber
    String address


    static constraints = {
        name nullable: false, blank: false
        email nullable: false, blank: false, unique: true
        password nullable: false, blank: false, unique: true
        phoneNumber nullable: false, blank: false,unique: true
        address nullable: false, blank: false
    }



}
