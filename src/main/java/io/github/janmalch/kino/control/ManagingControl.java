package io.github.janmalch.kino.control;

import io.github.janmalch.kino.util.Manageable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class ManagingControl<P> implements Control<P>, Manageable {

  private final Set<Manageable> resources = new HashSet<>();

  public abstract <T> T compute(ResultBuilder<T, P> result);

  protected void manage(Manageable... resource) {
    this.resources.addAll(Arrays.asList(resource));
  }

  @Override
  public final <T> T execute(ResultBuilder<T, P> result) {
    try {
      return compute(result);
    } finally {
      this.close();
    }
  }

  @Override
  public void close() {
    resources.forEach(Manageable::close);
  }
}
