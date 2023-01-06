package com.durys.jakub.organistationstructure.infrastructure.output

import com.durys.jakub.organistationstructure.commons.DomainEvent
import com.durys.jakub.organistationstructure.commons.EventPublisher
import org.springframework.amqp.rabbit.core.RabbitTemplate

open class RabbitmqEventPublisher(private val template: RabbitTemplate): EventPublisher {

    override fun publish(event: DomainEvent) {
        template.convertAndSend(event)
    }
}