//package com.misim.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import java.util.HashMap;
//import javax.sql.DataSource;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
//
//@Configuration
//public class DataSourceConfig {
//
//    private static final String SOURCE_SERVER = "SOURCE";
//    private static final String REPLICA_SERVER = "REPLICA";
//
//    @Bean
//    @Primary
//    public DataSource dataSource() {
//        DataSource determinedDataSource = routingDataSource(source(), replica());
//        return new LazyConnectionDataSourceProxy(determinedDataSource);
//    }
//
//    @Bean
//    @Qualifier(SOURCE_SERVER)
//    @ConfigurationProperties(prefix = "spring.datasource.source")
//    public DataSource source() {
//        return new HikariDataSource();
//    }
//
//    @Bean
//    @Qualifier(REPLICA_SERVER)
//    @ConfigurationProperties(prefix = "spring.datasource.replica")
//    public DataSource replica() {
//        return new HikariDataSource();
//    }
//
//    @Bean
//    public DataSource routingDataSource(@Qualifier(SOURCE_SERVER) DataSource source, @Qualifier(REPLICA_SERVER) DataSource replica) {
//        RoutingDataSource routingDataSource = new RoutingDataSource();
//
//        HashMap<Object, Object> targetDataSources = new HashMap<>();
//        targetDataSources.put(SOURCE_SERVER, source);
//        targetDataSources.put(REPLICA_SERVER, replica);
//
//        routingDataSource.setTargetDataSources(targetDataSources);
//        routingDataSource.setDefaultTargetDataSource(source);
//
//        return routingDataSource;
//    }
//}
