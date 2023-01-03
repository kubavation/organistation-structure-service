package com.durys.jakub.organistationstructure.infrastructure

import com.durys.jakub.organistationstructure.domain.StructureEntryRepository
import com.durys.jakub.organistationstructure.infrastructure.output.MongoStructureEntryRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate

@Configuration
class OrganisationStructureConfiguration {

    @Bean
    fun structureEntryRepository(mongoTemplate: MongoTemplate): StructureEntryRepository = MongoStructureEntryRepository(mongoTemplate)

}
