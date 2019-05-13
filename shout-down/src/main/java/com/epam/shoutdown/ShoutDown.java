package com.epam.shoutdown;

import java.util.Collections;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;

public class ShoutDown {

    private static boolean skipOnStartUp = true;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final ApplicationContext appContext;
    private String tableName;
    private Integer limitCount;

    public ShoutDown(DataSource dataSource, ApplicationContext appContext) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.appContext = appContext;
    }

    @Scheduled(fixedDelay = 60000)
    public void shoutDownOnCondition() {
        if (skipOnStartUp) {
            skipOnStartUp = false;
        } else if (shouldShoutDown()) {
            System.out.println("muhahaha");
            SpringApplication.exit(appContext, () -> -1);
        }
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

    private boolean shouldShoutDown() {
        String sql;
        Map<String, Object> parameters;
        sql = String.format("Select count(*) from %s", tableName);
        parameters = Collections.emptyMap();
        Integer totalCount = namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);

        return totalCount != null && totalCount >= limitCount;
    }
}
