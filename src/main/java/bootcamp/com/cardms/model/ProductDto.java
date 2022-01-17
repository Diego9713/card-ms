package bootcamp.com.cardms.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
  private String id;
  private String accountType;
  private String accountNumber;
  private String currency;
  private double amount;
  private double maintenanceCommission;
  private double creditLimit;
  private int maxTransactNumber;
  private LocalDateTime maintenanceCommissionDay;
  private LocalDateTime transactNumberDay;
  private String customer;
  private String status;
}
