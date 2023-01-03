package com.durys.jakub.organistationstructure.infrastructure.output

import org.springframework.amqp.rabbit.core.RabbitTemplate
import java.util.Objects

class RabbitmqEventPublisher(val template: RabbitTemplate) {

    fun publish(event: String) { //todo
        template.convertAndSend(event)
    }
}