package com.example.service

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaClient
interface MessageProducer {

    @Topic("demo-topic")
    def sendMessage(def user)

}