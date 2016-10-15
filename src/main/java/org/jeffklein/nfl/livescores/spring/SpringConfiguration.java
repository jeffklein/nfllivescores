package org.jeffklein.nfl.livescores.spring;

import org.jeffklein.nfl.livescores.hibernate.config.HibernateConfiguration;
import org.jeffklein.nfl.livescores.hibernate.dao.GameDao;
import org.jeffklein.nfl.livescores.hibernate.dao.GameDaoImpl;
import org.jeffklein.nfl.livescores.parser.NflDotComLiveScoresParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Import(HibernateConfiguration.class)
public class SpringConfiguration {
    @Bean
    public GameDao gameDao() {
        return new GameDaoImpl();
    }

    @Bean
    public NflDotComLiveScoresParser nflDotComLiveScoresParser() {
        return new NflDotComLiveScoresParser();
    }
}