package bootcamp.com.cardms.expose;

import bootcamp.com.cardms.business.ICardService;
import bootcamp.com.cardms.model.Card;
import bootcamp.com.cardms.model.CardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/cards")
public class CardController {
    @Autowired
    @Qualifier("CardService")
    private ICardService cardService;

    @GetMapping("")
    public Flux<CardDto> findAllCard() {
        return cardService.findAllCard();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CardDto>> findOneCard(@PathVariable String id) {
        return cardService.findByIdCard(id)
                .flatMap(p -> Mono.just(ResponseEntity.ok().body(p)))
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));

    }

    @PostMapping("")
    public Mono<ResponseEntity<CardDto>> saveCard(@RequestBody Card card) {
        return cardService.createCard(card)
                .flatMap(p -> Mono.just(ResponseEntity.ok().body(p)))
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CardDto>> updateCard(@PathVariable String id, @RequestBody Card card) {
        return cardService.updateCard(card, id)
                .flatMap(p -> Mono.just(ResponseEntity.ok().body(p)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/product/{productId}")
    public Mono<ResponseEntity<CardDto>> removeCardByProduct(@PathVariable("productId") String id) {
        return cardService.removeCardByProductId(id)
                .flatMap(p -> Mono.just(ResponseEntity.ok().body(p)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<CardDto>> removeCard(@PathVariable("id") String id) {
        return cardService.removeCard(id)
                .flatMap(p -> Mono.just(ResponseEntity.ok().body(p)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}

