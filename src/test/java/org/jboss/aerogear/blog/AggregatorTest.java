package org.jboss.aerogear.blog;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author edewit
 */
public class AggregatorTest {
  @Test
  public void testCreateFeed() throws Exception {
    // given
    final Feeds feeds = mock(Feeds.class);
    Aggregator aggregator = new Aggregator(feeds);

    // when
    when(feeds.getFeeds()).thenReturn(getMockedList());
    final String feed = aggregator.createFeed();

    // then
    assertNotNull(feed);
    final String expectedString = IOUtils.toString(getClass().getResourceAsStream("/result.xml"));

    assertThat(feed, equalToIgnoringWhiteSpace(expectedString));
  }

  private List<Feed> getMockedList() throws MalformedURLException {
    List<Feed> urls = new ArrayList<>();
    urls.add(new Feed("test@test.com", getClass().getResource("/blog2.xml").toString()));
    urls.add(new Feed("edewit@redhat.com", getClass().getResource("/blog1.xml").toString()));
    return urls;
  }
}
