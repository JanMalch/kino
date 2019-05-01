package io.github.janmalch.kino.problem;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProblemSerializer extends JsonSerializer<Problem> {

  private Logger logger = LoggerFactory.getLogger(ProblemSerializer.class);

  @Override
  public void serialize(Problem problem, JsonGenerator jgen, SerializerProvider serializerProvider)
      throws IOException {
    jgen.writeStartObject();

    logger.info("aYYYY KAOADN");

    jgen.writeStringField("type", problem.getType().toString());
    if (problem.getTitle() != null) {
      jgen.writeStringField("title", problem.getTitle());
    }
    if (problem.getStatus() != null) {
      jgen.writeNumberField("status", problem.getStatus().getStatusCode());
    }
    if (problem.getDetail() != null) {
      jgen.writeStringField("detail", problem.getDetail());
    }
    if (problem.getInstance() != null) {
      jgen.writeStringField("instance", problem.getInstance().toString());
    }

    problem
        .getParameters()
        .forEach(
            (key, value) -> {
              try {
                jgen.writeObjectField(key, value);
              } catch (IOException e) {
                var message =
                    String.format("Skipping custom parameter '%s' with value '%s'", key, value);
                logger.error(message, e);
              }
            });

    jgen.writeEndObject();
  }
}
