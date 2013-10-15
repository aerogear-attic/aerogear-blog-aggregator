package org.jboss.aerogear.blog;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author edewit
 */
public class FeedsTest {

  @Test
  public void shouldParseYmlWithFeedUrls() throws MalformedURLException {
    // given
    Feeds feeds = new Feeds("/test.yml");

    // when
    final List<URL> feedList = feeds.getFeeds();

    // then
    assertNotNull(feedList);
    assertEquals(2, feedList.size());
    URL myFeed = feedList.get(0);
    assertEquals("http://blog.nerdin.ch/feeds/posts/default/-/aerogear", myFeed.toString());
  }
}
