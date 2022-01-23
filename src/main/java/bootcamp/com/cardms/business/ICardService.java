package bootcamp.com.cardms.business;

import bootcamp.com.cardms.model.dto.CardAmountDto;
import bootcamp.com.cardms.model.dto.CardDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICardService {

  Flux<CardDto> findAllCard();

  Mono<CardDto> findByIdCard(String id);

  Mono<CardAmountDto> findBalanceCard(String cardNumber);

  Mono<CardDto> createCard(CardDto card);

  Mono<CardDto> updateCard(CardDto card, String id);

  Mono<CardDto> removeCardByProductId(String id);

  Mono<CardDto> removeCard(String id);

}
