package io.github.janmalch.kino.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ReflectionMapperTest {

  @Test
  void map() {
    var mapper = new ReflectionMapper<TestEntity, TestDTO>(TestDTO.class);
    var entity = new TestEntity();
    entity.setId(1L);
    entity.setName("Captain Marvel");
    entity.setAge(100);

    var domain = mapper.map(entity);
    assertEquals("Captain Marvel", domain.getName());
  }

  @Test
  void update() {
    var mapper = new ReflectionMapper<TestEntity, TestDTO>(TestDTO.class);
    var entity = new TestEntity();
    entity.setId(1L);
    entity.setName("Captain Marvel");
    entity.setAge(100);

    var domain = mapper.map(entity);
    domain.setAge(200);

    var reverseMapper = new ReflectionMapper<TestDTO, TestEntity>(TestEntity.class);
    var newEntity = reverseMapper.update(domain, entity);
    assertEquals(1L, newEntity.getId());
    assertEquals(200, newEntity.getAge());
    assertEquals("Captain Marvel", newEntity.getName(), "Empty fields should not be used");
  }

  static final class TestDTO {
    private String name;
    private int age;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }
  }

  static final class TestEntity {
    private String name;
    private int age;
    private long id;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }

    public long getId() {
      return id;
    }

    public void setId(long id) {
      this.id = id;
    }
  }
}
