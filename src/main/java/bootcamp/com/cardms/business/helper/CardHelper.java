package bootcamp.com.cardms.business.helper;

import bootcamp.com.cardms.model.Card;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class CardHelper {
    /**
     * Method to create the card object with all the necessary parameters.
     *
     * @param card -> card object with entered data.
     * @return a card with complete data.
     */
    public Mono<Card> setObjectCardBySave(Card card) {
        Card objCard = new Card();
        objCard.setCardType(card.getCardType().toUpperCase());
        objCard.setProductId(card.getProductId());
        objCard.setCardNumber(UUID.randomUUID().toString());
        objCard.setCvv(generateCVV());
        objCard.setMonth(LocalDate.now().getMonthValue());
        objCard.setYear(LocalDate.now().plusYears(5).getYear());
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
     * Method to generate a cvv.
     *
     * @return the cvv generate.
     */
    public String generateCVV() {
        String cvv = "";
        for (int i = 0; i < 3; i++) {
            int v = (int) Math.floor(Math.random() * 9 + 1);
            cvv = cvv + v;
        }
        return cvv;
    }
}
