package com.durys.jakub.organistationstructure.infrastructure.output

import org.springframework.amqp.rabbit.core.RabbitTemplate

class RabbitmqEventPublisher(private val template: RabbitTemplate) {

    fun publish(event: String) { //todo
        template.convertAndSend(event)
    }
}