package com.epam.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "shutdown.condition")
public class ShoutDownProperties {

    private String tableName = "USER";
    private Integer countOfRecord = 2;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getCountOfRecord() {
        return countOfRecord;
    }

    public void setCountOfRecord(Integer countOfRecord) {
        this.countOfRecord = countOfRecord;
    }
}
