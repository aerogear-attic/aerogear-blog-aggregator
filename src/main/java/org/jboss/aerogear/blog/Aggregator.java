package org.jboss.aerogear.blog;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;
import com.sun.syndication.io.XmlReader;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author edewit@redhat.com
 */
public class Aggregator {

  private final Feeds feeds;

  public Aggregator(Feeds feeds) {
    this.feeds = feeds;
  }

  @SuppressWarnings("unchecked")
  public String createFeed() throws IOException, FeedException {
    SyndFeed feed = new SyndFeedImpl();
    feed.setFeedType("rss_2.0");

    feed.setTitle("Aggregated AeroGear Blogs");
    feed.setDescription("Aggregated Feed of blogs dedicated to aerogear");
    feed.setAuthor("aerogear");
    feed.setLink("http://aerogear.org");

    List entries = new ArrayList();
    feed.setEntries(entries);

    for (URL url : feeds.getFeeds()) {
      SyndFeedInput input = new SyndFeedInput();
      SyndFeed inFeed = input.build(new XmlReader(url));

      entries.addAll(inFeed.getEntries());
    }

    SyndFeedOutput output = new SyndFeedOutput();
    final StringWriter writer = new StringWriter();
    output.output(feed, writer);

    return writer.toString();
  }
}
