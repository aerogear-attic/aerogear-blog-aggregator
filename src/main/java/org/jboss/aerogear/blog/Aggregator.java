package org.jboss.aerogear.blog;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;
import com.sun.syndication.io.XmlReader;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.*;

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

    for (URL url : feeds.getFeeds()) {
      SyndFeedInput input = new SyndFeedInput();
      SyndFeed inFeed = input.build(new XmlReader(url));

      entries.addAll(inFeed.getEntries());
    }

    SyndEntry[] entriesArray = (SyndEntry[]) entries.toArray(new SyndEntry[entries.size()]);
    Arrays.sort(entriesArray, new OrderByDate());
    feed.setEntries(Arrays.asList(entriesArray));

    SyndFeedOutput output = new SyndFeedOutput();
    final StringWriter writer = new StringWriter();
    output.output(feed, writer);

    return writer.toString();
  }

  private class OrderByDate implements Comparator<SyndEntry> {
    @Override
    public int compare(SyndEntry o1, SyndEntry o2) {
      final Date publishedDate1 = o2.getPublishedDate();
      return publishedDate1 != null ? publishedDate1.compareTo(o1.getPublishedDate()) : -1;
    }
  }
}
