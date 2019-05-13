package com.epam.autoconfigure;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.epam.shoutdown.ShoutDown;

@Configuration
@EnableConfigurationProperties(ShoutDownProperties.class)
@ConditionalOnClass(NamedParameterJdbcTemplate.class)
@EnableScheduling
public class ShoutDownAutoConfiguration {

    private final ApplicationContext appContext;
    private final DataSource dataSource;
    private final ShoutDownProperties shoutDownProperties;

    @Autowired
    public ShoutDownAutoConfiguration(ApplicationContext appContext, DataSource dataSource, ShoutDownProperties shoutDownProperties) {
        this.appContext = appContext;
        this.dataSource = dataSource;
        this.shoutDownProperties = shoutDownProperties;
    }

    @Bean
    public ShoutDown shoutDownBean() {
        ShoutDown shoutDown = new ShoutDown(dataSource, appContext);
        shoutDown.setTableName(shoutDownProperties.getTableName());
        shoutDown.setLimitCount(shoutDownProperties.getCountOfRecord());
        return shoutDown;
    }
}
