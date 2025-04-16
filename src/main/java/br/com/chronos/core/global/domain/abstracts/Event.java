package br.com.chronos.core.global.domain.abstracts;

public abstract class Event<Payload> {
  private final Payload payload;

  public Event(Payload payload) {
    this.payload = payload;
  }

  public Object getPayload() {
    return payload;
  }
}
