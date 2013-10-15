package org.jboss.aerogear.blog;

import org.yaml.snakeyaml.Yaml;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author edewit
 */
public class Feeds {
  public static final String BLOGS_RESOURCE = "blogs.yml";
  private String resourceName;
  private Yaml yaml = new Yaml();

  public Feeds() {}

  protected Feeds(String resourceName) {
    this.resourceName = resourceName;
  }

  @SuppressWarnings("unchecked")
  public List<URL> getFeeds() {
    final String name = resourceName != null ? resourceName : "/" + BLOGS_RESOURCE;
    List<String> urls = (List<String>) yaml.load(getClass().getResourceAsStream(name));

    List<URL> result = new ArrayList<URL>();
    for (String url : urls) {
      try {
        result.add(new URL(url));
      } catch (MalformedURLException e) {
        throw new RuntimeException("malformed url in yaml resource file " + name);
      }
    }

    return result;
  }


}
