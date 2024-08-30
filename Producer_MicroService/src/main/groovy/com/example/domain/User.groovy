package com.example.domain

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
class User {
    long id
    String name
    String email
    String password
    String phoneNumber
    String address
}
