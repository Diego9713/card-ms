package bootcamp.com.cardms.business;

import bootcamp.com.cardms.model.Card;
import bootcamp.com.cardms.model.CardDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICardService {

    Flux<CardDto> findAllCard();

    Mono<CardDto> findByIdCard(String id);

    Mono<CardDto> createCard(Card card);

    Mono<CardDto> updateCard(Card card, String id);

    Mono<CardDto> removeCardByProductId(String id);

    Mono<CardDto> removeCard(String id);

}
