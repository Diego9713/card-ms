package bootcamp.com.cardms.utils;

import bootcamp.com.cardms.model.Card;
import bootcamp.com.cardms.model.dto.CardDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppUtilTest {

  private static final Card card = new Card();
  private static final CardDto cardDto = new CardDto();
  private static final String id = "61dcaf570feb3d339e4c04b0";
  private static final String productId = "61db64d731dec743727907f3";
  private static final String cardNumber = "28101231-0512-4eb4-951f-3a7e3a49811f";
  private static final String cardType = "VISA";
  private static final String cvv = "215";
  private static final int month = 1;
  private static final int year = 2027;
  private static final String status = "ACTIVE";

  @BeforeAll
  static void setUp(){
    card.setId(id);
    card.setProductId(productId);
    card.setCardNumber(cardNumber);
    card.setCardType(cardType);
    card.setCvv(cvv);
    card.setMonth(month);
    card.setYear(year);
    card.setStatus(status);
    BeanUtils.copyProperties(card,cardDto);
  }
  @Test
  void CardDto() {
    Assertions.assertNotNull(AppUtil.entityToCardDto(card));
  }

  @Test
  void Entity() {
    Assertions.assertNotNull(AppUtil.cardDtoToEntity(cardDto));
  }
}