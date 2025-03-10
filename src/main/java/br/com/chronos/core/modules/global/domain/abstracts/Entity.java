package br.com.chronos.core.modules.global.domain.abstracts;

import java.lang.reflect.Field;
import java.util.Objects;

import br.com.chronos.core.modules.global.domain.records.Id;

public abstract class Entity {
  private final Id id;

  protected Entity(String id) {
    this.id = (id != null) ? Id.create(id, this.getClass().getName() + " Id") : Id.random();
  }

  public Id getId() {
    return id;
  }

  @Override
  public boolean equals(Object object) {
    Entity entity = (Entity) object;
    return Objects.equals(getId().toString(), entity.getId().toString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    Field[] fields = getClass().getDeclaredFields();
    var className = getClass().getSimpleName();
    StringBuilder fieldsString = new StringBuilder(className + " {\n");

    for (Field field : fields) {
      field.setAccessible(true);
      try {
        fieldsString.append("\t").append(field.getName()).append(": ").append(field.get(this)).append(",\n");
      } catch (IllegalAccessException e) {
        fieldsString.append(field.getName()).append(": <inaccessible>, ");
      }
    }

    if (fields.length > 0) {
      fieldsString.setLength(fieldsString.length() - 2);
    }

    return fieldsString.append("\n}").toString();
  }
}
