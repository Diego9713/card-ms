package bootcamp.com.cardms.model;

import bootcamp.com.cardms.utils.ConstantsCardStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Getter
@Setter
@NoArgsConstructor
@Document(collection = "card")
public class Card {
  @Id
  private String id;
  @Field(name = "product_id")
  private String productId;
  @Field(name = "card_number")
  private String cardNumber;
  @Field(name = "card_type")
  private String cardType;
  @Field(name = "cvv")
  private String cvv;
  @Field(name = "month")
  private int month;
  @Field(name = "year")
  private int year;
  @Field(name = "status")
  private String status = ConstantsCardStatus.ACTIVE.name();
}
