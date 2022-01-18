package bootcamp.com.cardms.business.helper;

import bootcamp.com.cardms.model.Card;
import bootcamp.com.cardms.model.CardDto;
import com.mongodb.assertions.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CardHelperTest {
  @Autowired
  private CardHelper cardHelper;

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
  static void setUp() {
    card.setId(id);
    card.setProductId(productId);
    card.setCardNumber(cardNumber);
    card.setCardType(cardType);
    card.setCvv(cvv);
    card.setMonth(month);
    card.setYear(year);
    card.setStatus(status);
    BeanUtils.copyProperties(card, cardDto);
  }

  @Test
  void setObjectCardBySave() {
    Assertions.assertNotNull(cardHelper.setObjectCardBySave(card));
  }

  @Test
  void setObjectCardByUpdate() {
    Card findCard = new Card();
    BeanUtils.copyProperties(card,findCard);
    Assertions.assertNotNull(cardHelper.setObjectCardByUpdate(card,findCard));
  }

  @Test
  void generateCvv() {
    Assertions.assertNotNull(cardHelper.generateCvv());
  }
}