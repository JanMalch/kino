package io.github.janmalch.kino.entity;

import java.util.ArrayList;
import java.util.List;

public enum Role {
  ADMIN(null),
  MODERATOR(ADMIN),
  CUSTOMER(MODERATOR),
  GUEST(CUSTOMER);

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

  List<Role> getAllChildren() {
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

  public boolean hasMinRole(Role role) {
    List<Role> list = this.getAllChildren();
    list.add(this);
    return list.contains(role);
  }
}
