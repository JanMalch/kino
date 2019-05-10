package io.github.janmalch.kino.entity;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityWiper {

  private EntityManagerFactory factory = Persistence.createEntityManagerFactory("kino");
  private EntityManager em = factory.createEntityManager();

  /**
   * Deletes all tables from Database, but only if none of them have integrity constraints
   *
   * @deprecated use {@link #wipeDB()} instead
   * @throws IOException
   * @throws ClassNotFoundException
   */
  @Deprecated
  public void deleteAll() throws IOException, ClassNotFoundException {
    var sum =
        getClasses("io.github.janmalch.kino.entity")
            .stream()
            .filter(c -> c.isAnnotationPresent(Entity.class))
            .map(Class::getSimpleName)
            .mapToInt(
                entityClass -> {
                  System.out.println(">> DELETING EVERYTHING FROM " + entityClass);
                  em.getTransaction().begin();
                  var count = em.createQuery("DELETE FROM " + entityClass).executeUpdate();
                  em.getTransaction().commit();
                  return count;
                })
            .sum();
    System.out.println(">>>> DELETED " + sum + " entries in total!");
  }

  /** Drops all objects from database */
  public void wipeDB() {
    em.getTransaction().begin();
    em.createNativeQuery("DROP ALL OBJECTS").executeUpdate();
    em.getTransaction().commit();
  }

  // https://dzone.com/articles/get-all-classes-within-package

  /**
   * Scans all classes accessible from the context class loader which belong to the given package
   * and subpackages.
   *
   * @param packageName The base package
   * @return The classes
   * @throws ClassNotFoundException
   * @throws IOException
   */
  private ArrayList<Class<?>> getClasses(String packageName)
      throws ClassNotFoundException, IOException {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    assert classLoader != null;
    String path = packageName.replace('.', '/');
    Enumeration resources = classLoader.getResources(path);
    List<File> dirs = new ArrayList<>();
    while (resources.hasMoreElements()) {
      URL resource = (URL) resources.nextElement();
      String fileString = resource.getFile().replaceAll("%20", " ");
      dirs.add(new File(fileString));
    }
    ArrayList<Class<?>> classes = new ArrayList<>();
    for (File directory : dirs) {
      classes.addAll(findClasses(directory, packageName));
    }
    return classes;
  }

  /**
   * Recursive method used to find all classes in a given directory and subdirs.
   *
   * @param directory The base directory
   * @param packageName The package name for classes found inside the base directory
   * @return The classes
   * @throws ClassNotFoundException
   */
  private List<Class<?>> findClasses(File directory, String packageName)
      throws ClassNotFoundException {
    List<Class<?>> classes = new ArrayList<>();
    if (!directory.exists()) {
      return classes;
    }
    File[] files = directory.listFiles();
    for (File file : files) {
      if (file.isDirectory()) {
        assert !file.getName().contains(".");
        classes.addAll(findClasses(file, packageName + "." + file.getName()));
      } else if (file.getName().endsWith(".class")) {
        classes.add(
            Class.forName(
                packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
      }
    }
    return classes;
  }
}
