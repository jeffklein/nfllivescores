package org.jeffklein.nfl.livescores.web;

import org.jeffklein.nfl.livescores.hibernate.dao.GameDao;
import org.jeffklein.nfl.livescores.model.Game;
import org.jeffklein.nfl.livescores.parser.NflDotComLiveScoresParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GameRestController {

    @Autowired
    private GameDao gameDao;

    @Autowired
    private NflDotComLiveScoresParser parser;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public  Map<String, Object> index() throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<Game> games = this.gameDao.findAll();
        if (games.size() == 0) {
            // nothing in db yet. go find it on the web. this is just a hack because this is new. once the db starts getting populated this logic should be deleted
            games = parser.parse(new URL("http://www.nfl.com/liveupdate/scorestrip/scorestrip.json"));
        }
        model.put("league", "NFL");
        model.put("games-size", games.size());
        model.put("games", games);
        return model;
    }
}