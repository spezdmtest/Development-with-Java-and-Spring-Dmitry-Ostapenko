package com.griddynamics.finalprojectspring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.channel.DirectChannel;

@Configuration
@ImportResource("classpath:/integration/http-users-integration.xml")
public class UserIntegrationConfig {

    private DirectChannel usersChannel;

    @Autowired
    public UserIntegrationConfig(@Qualifier("usersChannel") DirectChannel usersChannel) {
        this.usersChannel = usersChannel;
    }

    public DirectChannel getUsersChannel() {
        return usersChannel;
    }
}
