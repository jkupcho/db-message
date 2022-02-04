package com.hcat.azure.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DbMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbMessageApplication.class, args);
    }

    private class HelloMessage {
        private final String message;

        private HelloMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    @RestController
    public class HelloController {

        private final MessageRepository repository;

        @Autowired
        public HelloController(MessageRepository repository) {
            this.repository = repository;
        }

        @GetMapping("/hello")
        public HelloMessage hello() {
            return this.repository.getMessageById(1);
        }

    }

    @Repository
    public class MessageRepository {

        private final String getMessageByIdSql = "select message from message where message_id = :id";

        private final NamedParameterJdbcTemplate jdbcTemplate;

        @Autowired
        public MessageRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        public HelloMessage getMessageById(int id) {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("id", id);

            return jdbcTemplate.queryForObject(getMessageByIdSql, parameterSource, (rs, rowNum) -> {
                String message = rs.getString(1);
                return new HelloMessage(message);
            });
        }

    }

}
