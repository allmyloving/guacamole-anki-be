package ua.serdiuk.guacamole.datastore

import com.google.cloud.datastore.DatastoreOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DatastoreConfiguration {
    @Bean
    fun datastore() = DatastoreOptions.getDefaultInstance().getService();
}