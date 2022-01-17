package bootcamp.com.cardms.business.impl;

import bootcamp.com.cardms.business.ICardService;
import bootcamp.com.cardms.business.helper.CardHelper;
import bootcamp.com.cardms.business.helper.WebClientProductHelper;
import bootcamp.com.cardms.model.Card;
import bootcamp.com.cardms.model.CardDto;
import bootcamp.com.cardms.repository.ICardRepository;
import bootcamp.com.cardms.utils.AppUtil;
import bootcamp.com.cardms.utils.ConstantsCardStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("CardService")
@Slf4j
public class CardService implements ICardService {
  @Autowired
  private ICardRepository cardRepository;
  @Autowired
  private CardHelper cardHelper;

  @Autowired
  private WebClientProductHelper webClientProductHelper;

  /**
   * Method to find all the cards.
   *
   * @return a list of cards.
   */
  @Override
  @Transactional(readOnly = true)
  public Flux<CardDto> findAllCard() {
    log.info("findAll cards >>>");
    return cardRepository.findAll().map(AppUtil::entityToCardDto)
      .filter(cardDto -> cardDto.getStatus().equalsIgnoreCase(ConstantsCardStatus.ACTIVE.name()));
  }

  /**
   * Method to search for a card by id.
   *
   * @param id -> is the unique identifier of the card.
   * @return a specific card.
   */
  @Override
  @Transactional(readOnly = true)
  public Mono<CardDto> findByIdCard(String id) {
    log.info("findOne card >>>");
    return cardRepository.findById(id).map(AppUtil::entityToCardDto)
      .filter(cardDto -> cardDto.getStatus().equalsIgnoreCase(ConstantsCardStatus.ACTIVE.name()));
  }

  /**
   * Method of saving a card.
   *
   * @param card -> it is an object with the card data.
   * @return the saved card.
   */
  @Override
  @Transactional
  public Mono<CardDto> createCard(CardDto card) {
    log.info("save card >>>");
    Card cardEntity = AppUtil.cardDtoToEntity(card);
    return cardRepository.findByProductId(card.getProductId())
      .switchIfEmpty(Mono.just(new Card()))
      .filter(findCard -> findCard.getProductId() == null)
      .flatMap(card1 -> cardHelper.setObjectCardBySave(cardEntity))
      .flatMap(cardRepository::insert)
      .map(AppUtil::entityToCardDto);
  }

  /**
   * Method to update a card.
   *
   * @param card -> it is an object with the card data.
   * @param id   -> is the unique identifier of the card.
   * @return the updated card.
   */
  @Override
  @Transactional
  public Mono<CardDto> updateCard(CardDto card, String id) {
    log.info("update card >>>");
    Card cardEntity = AppUtil.cardDtoToEntity(card);
    return cardRepository.findById(id)
      .switchIfEmpty(Mono.just(new Card()))
      .flatMap(findCard -> cardRepository.findByProductId(findCard.getProductId())
        .flatMap(card1 -> cardHelper.setObjectCardByUpdate(cardEntity, findCard))
        .flatMap(cardRepository::save))
      .map(AppUtil::entityToCardDto);

  }

  /**
   * Method to change the state of a card from the product microservice.
   * it is used from the product microservice.
   *
   * @param id -> is the identifier of the product account.
   * @return the card with change state.
   */
  @Override
  @Transactional
  public Mono<CardDto> removeCardByProductId(String id) {
    log.info("remove cardByProduct>>>");
    return cardRepository.findByProductId(id)
      .switchIfEmpty(Mono.just(new Card()))
      .doOnNext(card -> card.setStatus(ConstantsCardStatus.INACTIVE.name()))
      .flatMap(cardRepository::save)
      .map(AppUtil::entityToCardDto);
  }

  /**
   * Method to change the state of a card.
   * connects to the product microservice and changes the status of the card and the product.
   *
   * @param id -> is the identifier of the product account.
   * @return the card with change state.
   */
  @Override
  @Transactional
  public Mono<CardDto> removeCard(String id) {
    log.info("remove card >>>");
    return cardRepository.findById(id)
      .switchIfEmpty(Mono.just(new Card()))
      .flatMap(card -> webClientProductHelper.deleteProduct(card.getProductId())
        .flatMap(isBool -> Boolean.TRUE.equals(isBool) ? Mono.just(new CardDto()) : Mono.empty()));

  }

}
