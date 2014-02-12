package org.jboss.aerogear.blog;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author edewit@redhat.com
 */
public class Feed {
  private final String email;
  private final URL url;

  public Feed(String email, String url) throws MalformedURLException {
    this.email = email;
    this.url = new URL(url);
  }

  public String getEmail() {
    return email;
  }

  public URL getUrl() {
    return url;
  }
}
