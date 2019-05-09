package io.github.janmalch.kino.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.*;
import org.junit.jupiter.api.Test;

class CORSFilterTest {

  @Test
  void filter() {
    var corsFilter = new CORSFilter();
    var context = new TestContainerResponseContext();
    corsFilter.filter(null, context);
    assertNotNull(context.getHeaders().getFirst("Access-Control-Allow-Origin"));
  }

  static class TestContainerResponseContext implements ContainerResponseContext {

    private MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();

    @Override
    public int getStatus() {
      return 0;
    }

    @Override
    public void setStatus(int code) {}

    @Override
    public Response.StatusType getStatusInfo() {
      return null;
    }

    @Override
    public void setStatusInfo(Response.StatusType statusInfo) {}

    @Override
    public MultivaluedMap<String, Object> getHeaders() {
      return headers;
    }

    @Override
    public MultivaluedMap<String, String> getStringHeaders() {
      return null;
    }

    @Override
    public String getHeaderString(String name) {
      return null;
    }

    @Override
    public Set<String> getAllowedMethods() {
      return null;
    }

    @Override
    public Date getDate() {
      return null;
    }

    @Override
    public Locale getLanguage() {
      return null;
    }

    @Override
    public int getLength() {
      return 0;
    }

    @Override
    public MediaType getMediaType() {
      return null;
    }

    @Override
    public Map<String, NewCookie> getCookies() {
      return null;
    }

    @Override
    public EntityTag getEntityTag() {
      return null;
    }

    @Override
    public Date getLastModified() {
      return null;
    }

    @Override
    public URI getLocation() {
      return null;
    }

    @Override
    public Set<Link> getLinks() {
      return null;
    }

    @Override
    public boolean hasLink(String relation) {
      return false;
    }

    @Override
    public Link getLink(String relation) {
      return null;
    }

    @Override
    public Link.Builder getLinkBuilder(String relation) {
      return null;
    }

    @Override
    public boolean hasEntity() {
      return false;
    }

    @Override
    public Object getEntity() {
      return null;
    }

    @Override
    public Class<?> getEntityClass() {
      return null;
    }

    @Override
    public Type getEntityType() {
      return null;
    }

    @Override
    public void setEntity(Object entity) {}

    @Override
    public void setEntity(Object entity, Annotation[] annotations, MediaType mediaType) {}

    @Override
    public Annotation[] getEntityAnnotations() {
      return new Annotation[0];
    }

    @Override
    public OutputStream getEntityStream() {
      return null;
    }

    @Override
    public void setEntityStream(OutputStream outputStream) {}
  }
}
