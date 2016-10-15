package org.jeffklein.nfl.livescores.parser.test;

import org.apache.log4j.Logger;
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

import java.net.URL;
import java.util.List;


/**
 * Created by Jeff on 10/12/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class NflDotComLiveScoresParserTest  implements ApplicationContextAware {

    final static Logger logger = Logger.getLogger(NflDotComLiveScoresParserTest.class);

    @Autowired
    private NflDotComLiveScoresParser parser;

    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Test
    public void testParse() throws Exception {
        Assert.assertNotNull(this.parser);
        Assert.assertNotNull(this.context);
        Resource jsonSample = context.getResource("classpath:nfl_live_scores_sample.json");
        URL url = jsonSample.getURL();
        List<Game> games = parser.parse(url);
        for (Game game : games) {
            logger.debug(game.toString());
        }
    }
}
