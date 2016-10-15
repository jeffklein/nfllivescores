package org.jeffklein.nfl.livescores.model;

/**
 * Created by Jeff on 10/12/2016.
 */
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel( Game.class )
public class Game_ {
    public static volatile SingularAttribute<Game, String> day;
    public static volatile SingularAttribute<Game, String> time;
    public static volatile SingularAttribute<Game, String> status;
    public static volatile SingularAttribute<Game, String> awayTeam;
    public static volatile SingularAttribute<Game, Integer> awayScore;
    public static volatile SingularAttribute<Game, String> homeTeam;
    public static volatile SingularAttribute<Game, Integer> homeScore;
    public static volatile SingularAttribute<Game, Integer> gameId;
    public static volatile SingularAttribute<Game, String> week;
    public static volatile SingularAttribute<Game, Integer> year;
}
