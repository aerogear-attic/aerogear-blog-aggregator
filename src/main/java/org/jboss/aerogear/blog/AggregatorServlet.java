package org.jboss.aerogear.blog;

import com.sun.syndication.io.FeedException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author edewit
 */
@WebServlet(name = "blog", urlPatterns = {"/*"})
public class AggregatorServlet extends HttpServlet {

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Aggregator aggregator = new Aggregator(new Feeds());

    PrintWriter writer = null;
    try {
      writer = response.getWriter();
      writer.print(aggregator.createFeed());
    } catch (FeedException e) {
      throw new RuntimeException("could not fetch feeds", e);
    } finally {
      if (writer != null) writer.close();
    }
  }
}
