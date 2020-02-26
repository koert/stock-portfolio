package zencode.sb.portfolio.port;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Koert Zeilstra
 * https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/GettingStarted.Java.01.html
 * java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb
 */
@Repository
public class PortfolioRepository {

  private static final Logger log = LoggerFactory.getLogger(PortfolioRepository.class);

  private AmazonDynamoDB dynamoClient = AmazonDynamoDBClientBuilder.standard()
      .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "eu-west-1"))
      .build();

  @PostConstruct
  public void initialize() {
      try {
        DynamoDB dynamoDB = new DynamoDB(dynamoClient);
        Table existingTable = dynamoDB.getTable("stockposition");
        if (existingTable == null) {
          List<KeySchemaElement> schema = Arrays.asList(
              new KeySchemaElement("userId", KeyType.HASH),
              new KeySchemaElement("symbol", KeyType.RANGE));
//       KeySchemaElement("name", KeyType.RANGE),
//       KeySchemaElement("amount", KeyType.RANGE),
//       KeySchemaElement("buyPrice", KeyType.RANGE),
//       KeySchemaElement("buyDate", KeyType.RANGE),
//       KeySchemaElement("latestPrice", KeyType.RANGE),
//       KeySchemaElement("latestDate", KeyType.RANGE));
          List<AttributeDefinition> attributes = Arrays.asList(
              new AttributeDefinition("userId", ScalarAttributeType.S),
              new AttributeDefinition("symbol", ScalarAttributeType.S));
//       AttributeDefinition("name", ScalarAttributeType.S),
//       AttributeDefinition("amount", ScalarAttributeType.N),
//       AttributeDefinition("buyPrice", ScalarAttributeType.S),
//       AttributeDefinition("buyDate", ScalarAttributeType.S),
//       AttributeDefinition("latestPrice", ScalarAttributeType.S),
//       AttributeDefinition("latestDate", ScalarAttributeType.S));
          Table table = dynamoDB.createTable("stockposition", schema, attributes,
              new ProvisionedThroughput(10L, 10L));
          table.waitForActive();
          log.debug("Success.  Table status: " + table.getDescription().getTableStatus());
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
  }

  /**
   * Retrieve all stock positions of userId.
   * @param userId User ID.
   * @return Retrieved stock positions.
   */
  public List<StockPosition> retrievePositions(String userId) {
    log.debug("retrievePositions: " + userId);
    DynamoDB dynamoDB = new DynamoDB(dynamoClient);
    Table table = dynamoDB.getTable("stockposition");

    QuerySpec spec = new QuerySpec()
        .withKeyConditionExpression("userId = :userId")
        .withValueMap(new ValueMap()
            .withString(":userId", userId));

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    ItemCollection<QueryOutcome> items = table.query(spec);
    List<StockPosition> positions = new ArrayList<>();

    try {
      Iterator<Item> iterator = items.iterator();
      Item item = null;
      while (iterator.hasNext()) {
        item = iterator.next();
        StockPosition position = new StockPosition();
        position.symbol = item.getString("symbol");
        position.amount = item.getInt("amount");
        position.buyPrice = new BigDecimal(item.getString("buyPrice"));
        position.buyDate = dateFormat.parse(item.getString("buyDate"));
        position.latestPrice = new BigDecimal(item.getString("latestPrice"));
        position.latestDate = dateFormat.parse(item.getString("latestDate"));
        positions.add(position);
      }

//      for (Item item : items) {
//        StockPosition position = new StockPosition();
//        position.symbol = item.getString("symbol");
//        position.amount = item.getInt("amount");
//        position.buyPrice = new BigDecimal(item.getString("buyPrice"));
//        position.buyDate = dateFormat.parse(item.getString("buyDate"));
//        position.latestPrice = new BigDecimal(item.getString("latestPrice"));
//        position.latestDate = dateFormat.parse(item.getString("latestDate"));
//        positions.add(position);
//      }
    } catch (ParseException e) {

    }
    return positions;
  }

  public void savePosition(String userId, StockPosition position) {
    log.debug("savePosition: " + userId + " " + position.symbol);
    DynamoDB dynamoDB = new DynamoDB(dynamoClient);
    Table table = dynamoDB.getTable("stockposition");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//    val valueMap = mapOf(
//       "amount" to position.amount,
//       "buyPrice" to position.buyPrice.toString(),
//       "buyDate" to dateFormat.format(position.buyDate),
//       "latestPrice" to position.latestPrice.toString(),
//       "latestDate" to dateFormat.format(position.latestDate)
//    );
    Item item = new Item().withPrimaryKey("userId", userId, "symbol", position.symbol)
        .with("amount", position.amount)
        .with("buyPrice", position.buyPrice.toString())
        .with("buyDate", dateFormat.format(position.buyDate))
        .with("latestPrice", position.latestPrice.toString())
        .with("latestDate", dateFormat.format(position.latestDate));
    table.putItem(item);
  }

  public void savePositions(String userId, List<StockPosition> positions) {
    for (StockPosition position : positions) {
      savePosition(userId, position);
    }
  }

}
