package com.durys.jakub.organistationstructure.commons

interface EventPublisher {
    fun publish(event: DomainEvent)
}