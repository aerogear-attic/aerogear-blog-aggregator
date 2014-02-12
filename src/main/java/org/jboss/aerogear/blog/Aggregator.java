package org.jboss.aerogear.blog;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;
import com.sun.syndication.io.XmlReader;

import java.io.IOException;
import java.io.StringWriter;
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
    SyndFeed syndFeed = new SyndFeedImpl();
    syndFeed.setFeedType("rss_2.0");

    syndFeed.setTitle("Aggregated AeroGear Blogs");
    syndFeed.setDescription("Aggregated Feed of blogs dedicated to aerogear");
    syndFeed.setAuthor("aerogear");
    syndFeed.setLink("http://aerogear.org");

    List blogFeeds = new ArrayList();

    for (Feed feed : feeds.getFeeds()) {
      SyndFeedInput input = new SyndFeedInput();
      SyndFeed inFeed = input.build(new XmlReader(feed.getUrl()));
      overrideAuthorEmail(feed, inFeed);

      blogFeeds.addAll(inFeed.getEntries());
    }

    SyndEntry[] entriesArray = (SyndEntry[]) blogFeeds.toArray(new SyndEntry[blogFeeds.size()]);
    Arrays.sort(entriesArray, new OrderByDate());
    syndFeed.setEntries(Arrays.asList(entriesArray));

    SyndFeedOutput output = new SyndFeedOutput();
    final StringWriter writer = new StringWriter();
    output.output(syndFeed, writer);

    return writer.toString();
  }

  private void overrideAuthorEmail(Feed feed, SyndFeed inFeed) {
    final SyndPerson author = new SyndPersonImpl();
    author.setEmail(feed.getEmail());
    for (Object entry : inFeed.getEntries()) {
      ((SyndEntryImpl) entry).setAuthors(Arrays.asList(author));
    }
  }

  private class OrderByDate implements Comparator<SyndEntry> {
    @Override
    public int compare(SyndEntry o1, SyndEntry o2) {
      final Date date1 = o1.getPublishedDate();
      final Date date2 = o2.getPublishedDate();

      return date1 != null && date2 != null ? date2.compareTo(date1) : -1;
    }
  }
}
