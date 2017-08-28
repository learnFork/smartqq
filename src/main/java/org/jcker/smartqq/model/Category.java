package org.jcker.smartqq.model;

import java.util.ArrayList;
import java.util.List;

public class Category
{
  private int index;
  private int sort;
  private String name;
  private List<Friend> friends = new ArrayList();

  public void addFriend(Friend friend) {
    this.friends.add(friend);
  }

  public String toString()
  {
    return "Category{index=" + this.index + ", sort=" + this.sort + ", name='" + this.name + '\'' + ", friends=" + this.friends + '}';
  }

  public static Category defaultCategory()
  {
    Category category = new Category();
    category.setIndex(0);
    category.setSort(0);
    category.setName("我的好友");
    return category;
  }

  public int getIndex() {
    return this.index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public int getSort() {
    return this.sort;
  }

  public void setSort(int sort) {
    this.sort = sort;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Friend> getFriends() {
    return this.friends;
  }

  public void setFriends(List<Friend> friends) {
    this.friends = friends;
  }
}
