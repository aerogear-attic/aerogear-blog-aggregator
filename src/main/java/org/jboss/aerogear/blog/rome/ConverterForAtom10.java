package org.jboss.aerogear.blog.rome;

import com.sun.syndication.feed.WireFeed;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * @author edewit
 */
public class ConverterForAtom10 extends com.sun.syndication.feed.synd.impl.ConverterForAtom10 {
  @Override
  public void copyInto(WireFeed feed, SyndFeed syndFeed) {
    super.copyInto(feed, syndFeed);
    for (Object object : syndFeed.getEntries()) {
      SyndEntry entry = (SyndEntry) object;
      if (entry.getPublishedDate() == null) {
        entry.setPublishedDate(entry.getUpdatedDate());
      }
    }
  }
}
