package bootcamp.com.cardms.business.helper;

import bootcamp.com.cardms.model.Card;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

import bootcamp.com.cardms.model.dto.CardAmountDto;
import bootcamp.com.cardms.model.dto.ProductDto;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CardHelper {

  private final Random random = SecureRandom.getInstanceStrong();

  public CardHelper() throws NoSuchAlgorithmException {
    // TODO document why this constructor is empty
  }

  /**
   * Method to create the card object with all the necessary parameters.
   *
   * @param card -> card object with entered data.
   * @return a card with complete data.
   */
  public Mono<Card> setObjectCardBySave(Card card, ProductDto productDto) {
    Card objCard = new Card();
    if (productDto.getLevel() == 1) {
      objCard.setCardType(card.getCardType().toUpperCase());
      objCard.setProductId(card.getProductId());
      objCard.setCardNumber(UUID.randomUUID().toString());
      objCard.setCvv(generateCvv());
      objCard.setMonth(LocalDate.now().getMonthValue());
      objCard.setYear(LocalDate.now().plusYears(5).getYear());
    }
    return Mono.just(objCard);
  }

  /**
   * Method to update the card object with all the necessary parameters.
   *
   * @param card     -> card object with entered data.
   * @param findCard -> object from the database.
   * @return a card with complete data.
   */
  public Mono<Card> setObjectCardByUpdate(Card card, Card findCard) {
    Card objCard = new Card();
    objCard.setCardType(card.getCardType().toUpperCase());
    objCard.setProductId(card.getProductId());
    objCard.setCardNumber(findCard.getCardNumber());
    objCard.setCvv(findCard.getCvv());
    objCard.setMonth(findCard.getMonth());
    objCard.setYear(findCard.getYear());
    objCard.setStatus(card.getStatus().toUpperCase());
    objCard.setId(findCard.getId());
    return Mono.just(objCard);
  }

  /**
   * Method to generate report balance of product.
   *
   * @return report generate.
   */
  public Mono<CardAmountDto> generateReportsBalance(ProductDto productDto, Card card) {
    CardAmountDto objCardAmountDto = new CardAmountDto();
    objCardAmountDto.setCardNumber(card.getCardNumber());
    objCardAmountDto.setCardType(card.getCardType());
    objCardAmountDto.setAmount(productDto.getAmount());
    objCardAmountDto.setStatus(card.getStatus());
    return Mono.just(objCardAmountDto);
  }

  /**
   * Method to generate a cvv.
   *
   * @return the cvv generate.
   */
  public String generateCvv() {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < 3; i++) {
      int v = random.nextInt(9);
      stringBuilder.append(v);
    }
    return stringBuilder.toString();
  }
}
