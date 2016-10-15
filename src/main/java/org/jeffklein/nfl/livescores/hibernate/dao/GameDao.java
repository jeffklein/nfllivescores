package org.jeffklein.nfl.livescores.hibernate.dao;

import org.jeffklein.nfl.livescores.model.Game;

import java.util.List;

/**
 * Created by Jeff on 10/12/2016.
 */
public interface GameDao {
    public void createGame(Game game);
    public void deleteGame(Game game);
    public void updateGame(Game game);
    public void createOrUpdateGame(Game game);
    public Game findById(Integer id);
    public List<Game> findAllGames();
    public List<Game> findAllGamesInYear(Integer year);
    public List<Game> findAllGamesInWeekAndYear(String week, Integer year);
}
