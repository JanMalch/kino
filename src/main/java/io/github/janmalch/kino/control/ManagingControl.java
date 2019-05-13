package io.github.janmalch.kino.control;

import static io.github.janmalch.kino.util.functions.FunctionUtils.uncheck;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class ManagingControl<P> implements Control<P>, Closeable {

  private final Set<Closeable> resources = new HashSet<>();

  public abstract <T> T compute(ResultBuilder<T, P> result);

  protected void manage(Closeable... resource) {
    this.resources.addAll(Arrays.asList(resource));
  }

  @Override
  public final <T> T execute(ResultBuilder<T, P> result) {
    var output = compute(result);
    uncheck(this::close);
    return output;
  }

  @Override
  public final void close() throws IOException {
    for (Closeable closeable : resources) {
      closeable.close();
    }
  }
}
