package org.jcker.smartqq.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="font")
public class Font
{
  public static final Font DEFAULT_FONT = defaultFont();
  private int[] style;
  private String color;

  @Id
  private String name;
  private int size;

  private static Font defaultFont()
  {
    Font font = new Font();
    font.setColor("000000");
    font.setStyle(new int[] { 0, 0, 0 });
    font.setName("宋体");
    font.setSize(10);
    return font;
  }

  public int[] getStyle()
  {
    return this.style;
  }

  public void setStyle(int[] style) {
    this.style = style;
  }

  public String getColor() {
    return this.color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSize() {
    return this.size;
  }

  public void setSize(int size) {
    this.size = size;
  }
}
