package nl.zencode.port.q.stock;

import javax.json.bind.annotation.JsonbProperty;

/**
 * @author Koert Zeilstra
 */
public class SearchMatch {

  @JsonbProperty("1. symbol")
  public String symbol;

  @JsonbProperty("2. name")
  public String name;

  @JsonbProperty("3. type")
  public String type;

  @JsonbProperty("4. region")
  public String region;

  @JsonbProperty("5. marketOpen")
  public String marketOpen;

  @JsonbProperty("6. marketClose")
  public String marketClose;

  @JsonbProperty("7. timezone")
  public String timezone;

  @JsonbProperty("8. currency")
  public String currency;

  @JsonbProperty("9. matchScore")
  public Float matchScore;
}
