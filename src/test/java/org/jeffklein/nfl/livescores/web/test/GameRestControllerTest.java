package org.jeffklein.nfl.livescores.web.test;

import org.apache.log4j.Logger;
import org.jeffklein.nfl.livescores.hibernate.dao.GameDao;
import org.jeffklein.nfl.livescores.model.Game;
import org.jeffklein.nfl.livescores.parser.NflDotComLiveScoresParser;
import org.jeffklein.nfl.livescores.web.GameRestController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameRestControllerTest implements ApplicationContextAware {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private GameDao gameDao;

    @Autowired
    private NflDotComLiveScoresParser parser;

    private ApplicationContext applicationContext;

    final static Logger logger = Logger.getLogger(GameRestController.class);

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Before
    public void setup() {
        Resource jsonSample = this.applicationContext.getResource("classpath:nfl_live_scores_sample.json");
        try {
            URL url = jsonSample.getURL();
            List<Game> games = this.parser.parse(url);
            for (Game game : games) {
                this.gameDao.createGame(game);
            }
        } catch (IOException ioe) {
            logger.error("couldn't get url from classpath resource!", ioe);
        }
    }

    @After
    public void tearDown() {
        List<Game> games = this.gameDao.findAllGames();
        for (Game game : games) {
            this.gameDao.deleteGame(game);
        }
    }

    @Test
    public void testGetAllGames() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.games", hasSize(14)))
                .andExpect(jsonPath("$.league", equalTo("NFL")))
                .andExpect(jsonPath("$.games-size", equalTo(14)))
                .andExpect(jsonPath("$.games[0].gameId", equalTo(56964)))
                .andExpect(jsonPath("$.games[0].awayTeam", equalTo("ARI")))
                .andExpect(jsonPath("$.games[0].homeTeam", equalTo("SF")))
                .andExpect(jsonPath("$.games[0].awayScore", equalTo(33)))
                .andExpect(jsonPath("$.games[0].homeScore", equalTo(21)))
                .andExpect(jsonPath("$.games[0].status", equalTo("Final")));
    }

    @Test
    public void testGetAllGamesIn2016() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/2016").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.games", hasSize(14)))
                .andExpect(jsonPath("$.league", equalTo("NFL")))
                .andExpect(jsonPath("$.games-size", equalTo(14)))
                .andExpect(jsonPath("$.games[0].gameId", equalTo(56964)))
                .andExpect(jsonPath("$.games[0].awayTeam", equalTo("ARI")))
                .andExpect(jsonPath("$.games[0].homeTeam", equalTo("SF")))
                .andExpect(jsonPath("$.games[0].awayScore", equalTo(33)))
                .andExpect(jsonPath("$.games[0].homeScore", equalTo(21)))
                .andExpect(jsonPath("$.games[0].status", equalTo("Final")));
    }

    @Test
    public void testGetAllGamesIn2010() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/2010").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.games", hasSize(0)))
                .andExpect(jsonPath("$.league", equalTo("NFL")))
                .andExpect(jsonPath("$.games-size", equalTo(0)));
    }

    @Test
    public void testGetAllGamesIn2016REG5() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/2016/REG5").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.games", hasSize(14)))
                .andExpect(jsonPath("$.league", equalTo("NFL")))
                .andExpect(jsonPath("$.games-size", equalTo(14)))
                .andExpect(jsonPath("$.games[0].gameId", equalTo(56964)))
                .andExpect(jsonPath("$.games[0].awayTeam", equalTo("ARI")))
                .andExpect(jsonPath("$.games[0].homeTeam", equalTo("SF")))
                .andExpect(jsonPath("$.games[0].awayScore", equalTo(33)))
                .andExpect(jsonPath("$.games[0].homeScore", equalTo(21)))
                .andExpect(jsonPath("$.games[0].status", equalTo("Final")));
    }

    @Test
    public void testGetAllGamesIn2016REG0() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/2016/REG0").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.games", hasSize(0)))
                .andExpect(jsonPath("$.league", equalTo("NFL")))
                .andExpect(jsonPath("$.games-size", equalTo(0)));
    }
}
