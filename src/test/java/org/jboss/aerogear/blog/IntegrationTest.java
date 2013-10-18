package org.jboss.aerogear.blog;

import org.apache.commons.io.IOUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;

/**
 * @author edewit
 */
@RunWith(Arquillian.class)
public class IntegrationTest {

  @Deployment
  public static WebArchive createDeployment() {
    return ShrinkWrap.createFromZipFile(WebArchive.class,
        Maven.resolver().loadPomFromFile("pom.xml")
            .resolve("aerogear-blog-aggregator:aerogear-blog-aggregator:1.0-SNAPSHOT")
            .withoutTransitivity().asSingleFile());
  }

  @Test
  public void shouldFetchResults() throws IOException {
    // when
    InputStream input = null;
    try {
      input = new URL("http://localhost:8181/test").openStream();
      final String rssFeed = IOUtils.toString(input);
      assertThat(rssFeed, startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"));
    } finally {
      closeQuietly(input);
    }
  }
}
