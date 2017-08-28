package org.jcker.domain;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BaseEntity
  implements Serializable, Cloneable
{
  public int hashCode()
  {
    return HashCodeBuilder.reflectionHashCode(this, new String[0]);
  }

  protected Object clone() throws CloneNotSupportedException
  {
    return super.clone();
  }

  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public boolean equals(Object obj)
  {
    return EqualsBuilder.reflectionEquals(this, obj, new String[0]);
  }
}
