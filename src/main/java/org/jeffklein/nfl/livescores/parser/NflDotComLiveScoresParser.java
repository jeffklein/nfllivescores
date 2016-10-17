package org.jeffklein.nfl.livescores.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.jeffklein.nfl.livescores.model.Game;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jeff on 10/11/2016.
 */
public class NflDotComLiveScoresParser {

    public List<Game> parse(URL url) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);

        List<Game> gamesList = null;
        try {
            JsonNode rootNode = mapper.readTree(url);
            JsonNode ss = rootNode.path("ss");
            gamesList = new ArrayList<Game>(ss.size());
            Iterator iter = ss.elements();
            while (iter.hasNext()) {
                ArrayNode gameNode = (ArrayNode) iter.next();
                Game game = new Game();
                game.setDay(gameNode.get(0).asText());
                game.setTime(gameNode.get(1).asText());
                game.setStatus(gameNode.get(2).asText());
                game.setTimeRemaining(gameNode.get(3).asText(null)); //default null
                game.setAwayTeam(gameNode.get(4).asText());
                game.setAwayScore(gameNode.get(5).asInt());
                game.setHomeTeam(gameNode.get(6).asText());
                game.setHomeScore(gameNode.get(7).asInt());
                game.setTeamInPossession(gameNode.get(8).asText(null)); //default null
                // 9th value is 0 or 1 (causes scorestrip entry to turn red (score change?))
                game.setGameId(gameNode.get(10).asInt());
                // 11th value showed "INT" after an interception. "TD" after a touchdown.
                // we can probably ignore this as we don't plan on being 100% real-time
                game.setWeek(gameNode.get(12).asText());
                game.setYear(gameNode.get(13).asInt());
                gamesList.add(game);
            }
        }
        catch (IOException ioe) {
            throw new RuntimeException("unable to parse games", ioe);
        }
        return gamesList;
    }
}
