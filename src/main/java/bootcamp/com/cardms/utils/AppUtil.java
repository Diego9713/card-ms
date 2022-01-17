package bootcamp.com.cardms.utils;

import bootcamp.com.cardms.model.Card;
import bootcamp.com.cardms.model.CardDto;
import org.springframework.beans.BeanUtils;

public class AppUtil {
  private AppUtil() {
  }

  /**
   * Method to modify the return of data.
   *
   * @param card -> card object with entered data.
   * @return object modified.
   */
  public static CardDto entityToCardDto(Card card) {
    CardDto cardDto = new CardDto();
    BeanUtils.copyProperties(card, cardDto);
    return cardDto;
  }

  /**
   * Method to modify the return of data.
   *
   * @param cardDto -> card object with entered data.
   * @return object modified.
   */
  public static Card cardDtoToEntity(CardDto cardDto) {
    Card card = new Card();
    BeanUtils.copyProperties(cardDto, card);
    return card;
  }
}
