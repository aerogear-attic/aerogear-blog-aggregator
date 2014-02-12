package org.jboss.aerogear.blog;

import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author edewit
 */
public class FeedsTest {

  @Test
  public void shouldParseYmlWithFeedUrls() {
    // given
    Feeds feeds = new Feeds("/test.yml");

    // when
    final List<Feed> feedList = feeds.getFeeds();

    // then
    assertNotNull(feedList);
    assertEquals(2, feedList.size());
    Feed myFeed = feedList.get(0);
    assertEquals("edewit@redhat.com", myFeed.getEmail());
    assertEquals("http://blog.nerdin.ch/feeds/posts/default/-/aerogear", myFeed.getUrl().toString());
  }

  @Test(expected = RuntimeException.class)
  public void shouldThrowExceptionOnInvalidUrl() {
    // given
    Feeds feeds = new Feeds("/invalid-url.yml");

    // when
    feeds.getFeeds();
  }
}
