package bootcamp.com.cardms.repository;

import bootcamp.com.cardms.model.Card;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ICardRepository extends ReactiveMongoRepository<Card, String> {
  Mono<Card> findByProductId(String productId);

  Mono<Card> findByCardNumber(String cardNumber);
}
