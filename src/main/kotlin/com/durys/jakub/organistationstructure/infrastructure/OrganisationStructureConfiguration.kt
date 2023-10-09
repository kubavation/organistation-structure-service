package com.durys.jakub.organistationstructure.infrastructure

import com.durys.jakub.organistationstructure.commons.EventPublisher
import com.durys.jakub.organistationstructure.domain.StructureEntryRepository
import com.durys.jakub.organistationstructure.infrastructure.output.RabbitmqEventPublisher
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class OrganisationStructureConfiguration {

    @Bean
    fun eventPublisher(rabbitTemplate: RabbitTemplate): EventPublisher = RabbitmqEventPublisher(rabbitTemplate)


}
