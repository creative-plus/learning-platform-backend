package ro.creativeplus.learningplatformbackend.utils;

import org.hashids.Hashids;
import org.springframework.stereotype.Component;

@Component
public class HashIds {
  private final Hashids hashids;

  public HashIds() {
    this.hashids = new Hashids("This is a salt.", 20);
  }

  public String encodeId(int id) {
    return this.hashids.encode(id);
  }

  public int decodeId(String hash) {
    return (int) this.hashids.decode(hash)[0];
  }
}
