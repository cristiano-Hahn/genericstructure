package com.projects.genericstructure

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@ConfigurationPropertiesScan
@SpringBootApplication
@EnableJpaRepositories(basePackages = ["com.projects.genericstructure.core.domain"])
@EntityScan(basePackages = ["com.projects.genericstructure.core"])
class GenericStructureApplication

fun main(args: Array<String>) {
    runApplication<GenericStructureApplication>(*args)
}
