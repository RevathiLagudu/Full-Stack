package com.example.model

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
class UserRequest {
    String name
    String email
    String password
    String phoneNumber
    String address

}
