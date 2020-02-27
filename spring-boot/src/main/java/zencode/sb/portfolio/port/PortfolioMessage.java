package zencode.sb.portfolio.port;

import java.util.List;

/**
 * @author Koert Zeilstra
 */
//@javax.xml.bind.annotation.XmlAccessorType(javax.xml.bind.annotation.XmlAccessType.FIELD)
//@javax.xml.bind.annotation.XmlRootElement
public class PortfolioMessage {
  public List<StockPosition> positions;

  public List<StockPosition> getPositions() {
    return positions;
  }

  public void setPositions(List<StockPosition> positions) {
    this.positions = positions;
  }
}
