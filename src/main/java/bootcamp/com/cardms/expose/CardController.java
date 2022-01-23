package bootcamp.com.cardms.expose;

import bootcamp.com.cardms.business.ICardService;
import bootcamp.com.cardms.model.dto.CardAmountDto;
import bootcamp.com.cardms.model.dto.CardDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/cards")
public class CardController {
  @Autowired
  @Qualifier("CardService")
  private ICardService cardService;

  /**
   * Method to find all the cards.
   *
   * @return a list of cards.
   */
  @GetMapping("")
  public Flux<CardDto> findAllCard() {
    return cardService.findAllCard();
  }

  /**
   * Method to search for a card by id.
   *
   * @param id -> is the unique identifier of the card.
   * @return a specific card.
   */
  @GetMapping("/{id}")
  public Mono<ResponseEntity<CardDto>> findOneCard(@PathVariable String id) {
    return cardService.findByIdCard(id)
      .flatMap(p -> Mono.just(ResponseEntity.ok().body(p)))
      .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));

  }

  /**
   * Method to Check main account balance.
   *
   * @param cardNumber -> is the card number of the card.
   * @return a specific card.
   */
  @GetMapping("/balance/{cardNumber}")
  public Mono<ResponseEntity<CardAmountDto>> findBalanceCard(@PathVariable("cardNumber") String cardNumber) {
    return cardService.findBalanceCard(cardNumber)
      .flatMap(p -> Mono.just(ResponseEntity.ok().body(p)))
      .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));

  }

  /**
   * Method of saving a card.
   *
   * @param card -> it is an object with the card data.
   * @return the saved card.
   */
  @PostMapping("")
  public Mono<ResponseEntity<CardDto>> saveCard(@RequestBody CardDto card) {
    return cardService.createCard(card)
      .flatMap(p -> Mono.just(ResponseEntity.ok().body(p)))
      .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));
  }

  /**
   * Method to update a card.
   *
   * @param card -> it is an object with the card data.
   * @param id   -> is the unique identifier of the card.
   * @return the updated card.
   */
  @PutMapping("/{id}")
  public Mono<ResponseEntity<CardDto>> updateCard(@PathVariable String id, @RequestBody CardDto card) {
    return cardService.updateCard(card, id)
      .flatMap(p -> Mono.just(ResponseEntity.ok().body(p)))
      .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  /**
   * Method to change the state of a card from the product microservice.
   * it is used from the product microservice.
   *
   * @param id -> is the identifier of the product account.
   * @return the card with change state.
   */
  @DeleteMapping("/product/{productId}")
  public Mono<ResponseEntity<CardDto>> removeCardByProduct(@PathVariable("productId") String id) {
    return cardService.removeCardByProductId(id)
      .flatMap(p -> Mono.just(ResponseEntity.ok().body(p)))
      .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  /**
   * Method to change the state of a card.
   * connects to the product microservice and changes the status of the card and the product.
   *
   * @param id -> is the identifier of the product account.
   * @return the card with change state.
   */

  @CircuitBreaker(name = "productCB", fallbackMethod = "fallBackDeleteProduct")
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<CardDto>> removeCard(@PathVariable("id") String id) {
    return cardService.removeCard(id)
      .flatMap(p -> Mono.just(ResponseEntity.ok().body(p)))
      .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  /**
   * Method CircuitBreaker to removeCard.
   *
   * @param ex -> this is exception error.
   * @return exception error.
   */
  public Mono<ResponseEntity<String>> fallBackDeleteProduct(@PathVariable("id") String id, RuntimeException ex) {
    return Mono.just(ResponseEntity.ok().body("Search for the " + id + " in the service not available."));
  }
}

