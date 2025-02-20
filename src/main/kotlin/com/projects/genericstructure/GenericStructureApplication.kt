package com.projects.genericstructure

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class GenericStructureApplication

fun main(args: Array<String>) {
    runApplication<GenericStructureApplication>(*args)
}
