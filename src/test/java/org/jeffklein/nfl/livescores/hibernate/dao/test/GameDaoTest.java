package org.jeffklein.nfl.livescores.hibernate.dao.test;

import org.apache.log4j.Logger;
import org.jeffklein.nfl.livescores.hibernate.dao.GameDao;
import org.jeffklein.nfl.livescores.model.Game;
import org.jeffklein.nfl.livescores.parser.NflDotComLiveScoresParser;
import org.jeffklein.nfl.livescores.spring.SpringConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Jeff on 10/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class GameDaoTest implements ApplicationContextAware {

    @Autowired
    private GameDao gameDao;

    @Autowired
    private NflDotComLiveScoresParser parser;

    private List<Game> games;

    final static Logger logger = Logger.getLogger(GameDaoTest.class);

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Resource jsonSample = applicationContext.getResource("classpath:nfl_live_scores_sample.json");
        try {
            URL url = jsonSample.getURL();
            this.games = this.parser.parse(url);
        }
        catch(IOException ioe) {
            logger.error("couldn't get url from classpath resource!", ioe);
        }
    }

    @Test
    public void testInsertUpdateAndDeleteOneGame() {
        Assert.assertNotNull(this.games);
        Assert.assertNotNull(this.gameDao);
        List<Game> ensureDbEmpty = this.gameDao.findAllGames();
        Assert.assertEquals(0, ensureDbEmpty.size());
        Assert.assertNotNull(this.games.get(0));
        Assert.assertTrue(this.games.size() > 0);
        this.gameDao.createGame(games.get(0));
        Game fromDb = this.gameDao.findById(this.games.get(0).getGameId());
        Assert.assertNotNull(fromDb);
        Assert.assertEquals(this.games.get(0).getGameId(), fromDb.getGameId());
        fromDb.setAwayScore(1000);
        this.gameDao.updateGame(fromDb);
        Game fromDb2 = this.gameDao.findById(this.games.get(0).getGameId());
        Assert.assertEquals((Integer) 1000, fromDb2.getAwayScore());
        this.gameDao.deleteGame(fromDb2);
        Game fromDb3 = this.gameDao.findById(this.games.get(0).getGameId());
        Assert.assertNull(fromDb3);
    }

    @Test
    public void testInsertUpdateFindAndDeleteAll() {
        Assert.assertNotNull(this.games);
        Assert.assertNotNull(this.gameDao);
        List<Game> ensureDbEmpty = this.gameDao.findAllGames();
        Assert.assertEquals(0, ensureDbEmpty.size());

        for (Game game : this.games) {
            this.gameDao.createGame(game);
        }

        List<Game> fromDb = this.gameDao.findAllGames();
        Assert.assertEquals(14, fromDb.size());
        logger.debug("found "+fromDb.size()+" records in result set");
        for (Game game : fromDb) {
            game.setStatus("testpassed!");
            this.gameDao.updateGame(game);
        }

        List<Game> fromDb2 = this.gameDao.findAllGames();
        Assert.assertEquals(14, fromDb2.size());
        for(Game game : fromDb2) {
            Assert.assertEquals("testpassed!", game.getStatus());
        }

        for (Game game : fromDb2) {
            this.gameDao.deleteGame(game);
        }

        List<Game> fromDb3 = this.gameDao.findAllGames();
        Assert.assertEquals(0, fromDb3.size());
    }

    @Test
    public void testFindAllGamesInYear() {
        Assert.assertNotNull(this.games);
        Assert.assertNotNull(this.gameDao);
        List<Game> ensureDbEmpty = this.gameDao.findAllGames();
        Assert.assertEquals(0, ensureDbEmpty.size());

        for (Game game : this.games) {
            this.gameDao.createGame(game);
        }
        List<Game> allIn2016 = this.gameDao.findAllGamesInYear(2016);
        Assert.assertEquals(14, allIn2016.size());

        List<Game> allIn2010 = this.gameDao.findAllGamesInYear(2010);
        Assert.assertEquals(0, allIn2010.size());

        for (Game game : allIn2016) {
            this.gameDao.deleteGame(game);
        }

        List<Game> emptyWhenDone = this.gameDao.findAllGames();
        Assert.assertEquals(0, emptyWhenDone.size());
    }

    @Test
    public void testFindAllGamesInWeekAndYear() {
        Assert.assertNotNull(this.games);
        Assert.assertNotNull(this.gameDao);
        List<Game> ensureDbEmpty = this.gameDao.findAllGames();
        Assert.assertEquals(0, ensureDbEmpty.size());

        for (Game game : this.games) {
            this.gameDao.createGame(game);
        }
        List<Game> allInWeekFive = this.gameDao.findAllGamesInWeekAndYear("REG5", 2016);
        Assert.assertEquals(14, allInWeekFive.size());

        List<Game> allInWeekSix = this.gameDao.findAllGamesInWeekAndYear("REG6", 2016);
        Assert.assertEquals(0, allInWeekSix.size());

        for (Game game : allInWeekFive) {
            this.gameDao.deleteGame(game);
        }

        List<Game> emptyWhenDone = this.gameDao.findAllGames();
        Assert.assertEquals(0, emptyWhenDone.size());
    }
}
