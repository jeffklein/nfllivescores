package org.jeffklein.nfl.livescores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

// the exclude is to avoid a nasty runtime exception (see link for details)
// http://stackoverflow.com/questions/38627491/spring-4-hibernate-5-org-springframework-orm-jpa-entitymanagerholder-cannot
@SpringBootApplication(exclude=HibernateJpaAutoConfiguration.class)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}