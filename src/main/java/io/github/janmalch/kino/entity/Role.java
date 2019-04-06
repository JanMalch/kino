package io.github.janmalch.kino.entity;

import java.util.ArrayList;
import java.util.List;

public enum Role {
  GUEST(null),
  CUSTOMER(GUEST),
  MODERATOR(CUSTOMER),
  ADMIN(MODERATOR);

  private Role parent;
  private List<Role> children = new ArrayList<>();

  Role(Role parent) {
    this.parent = parent;
    if (this.parent != null) {
      this.parent.addChild(this);
    }
  }

  void addChild(Role child) {
    this.children.add(child);
  }

  public List<Role> getAllChildren() {
    List<Role> list = new ArrayList<>();
    addChildren(this, list);
    return list;
  }

  void addChildren(Role root, List<Role> list) {
    list.addAll(root.children);
    for (Role child : root.children) {
      addChildren(child, list);
    }
  }
}
