package com.darglk.exposedpoc.config

import org.jetbrains.exposed.sql.Database
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class ExposedConfig(val dataSource: DataSource) {
    
    @Bean
    fun db() = Database.connect(dataSource)
}