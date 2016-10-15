package org.jeffklein.nfl.livescores.poller;

import org.apache.log4j.Logger;
import org.jeffklein.nfl.livescores.hibernate.dao.GameDao;
import org.jeffklein.nfl.livescores.model.Game;
import org.jeffklein.nfl.livescores.parser.NflDotComLiveScoresParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Jeff on 10/15/2016.
 */
@Component
public class NflDotComLiveScoresPoller {
    @Autowired
    private NflDotComLiveScoresParser parser;

    @Autowired
    private GameDao gameDao;

    final static Logger logger = Logger.getLogger(NflDotComLiveScoresPoller.class);

    /**
     * Cron expression is set to poll once per hour, on the hour.
     */
    @Scheduled(cron = "0 0 * * * *")
    public void pollNflDotComForScoreUpdates() {
        logger.info("cron job fired for pollNflDotComForScoreUpdates()");
        URL url = null;
        try {
            url = new URL("http://www.nfl.com/liveupdate/scorestrip/scorestrip.json");
        }
        catch (MalformedURLException mue) {
            throw new RuntimeException("couldn't parse url: "+ url, mue);
        }

        List<Game> games = this.parser.parse(url);
        for (Game game : games) {
            this.gameDao.createOrUpdateGame(game);
        }
    }
}
