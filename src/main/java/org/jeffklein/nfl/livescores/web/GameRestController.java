package org.jeffklein.nfl.livescores.web;

import org.jeffklein.nfl.livescores.hibernate.dao.GameDao;
import org.jeffklein.nfl.livescores.model.Game;
import org.jeffklein.nfl.livescores.parser.NflDotComLiveScoresParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GameRestController {

    @Autowired
    private GameDao gameDao;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public  Map<String, Object> findAllGames() throws Exception {
        List<Game> games = this.gameDao.findAllGames();
        return buildModel(games);
    }

    @RequestMapping(value = "/{year}", method = RequestMethod.GET, produces = "application/json")
    public  Map<String, Object> findAllGamesInYear(@PathVariable Integer year) throws Exception {
        List<Game> games = this.gameDao.findAllGamesInYear(year);
        return buildModel(games);
    }

    @RequestMapping(value = "/{year}/{week}", method = RequestMethod.GET, produces = "application/json")
    public  Map<String, Object> findAllGamesInWeekAndYear(@PathVariable Integer year, @PathVariable String week) throws Exception {
        List<Game> games = this.gameDao.findAllGamesInWeekAndYear(week, year);
        return buildModel(games);
    }

    private Map<String, Object> buildModel(List<Game> games) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("league", "NFL");
        model.put("games-size", games.size());
        model.put("games", games);
        return model;
    }
}