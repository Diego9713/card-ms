package bootcamp.com.cardms.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CardAmountDto {

  private String cardNumber;
  private String cardType;
  private double amount;
  private String status;
}
