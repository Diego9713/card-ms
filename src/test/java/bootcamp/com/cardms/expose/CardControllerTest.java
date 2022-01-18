package bootcamp.com.cardms.expose;

import bootcamp.com.cardms.business.impl.CardService;
import bootcamp.com.cardms.model.Card;
import bootcamp.com.cardms.model.CardDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "20000")
class CardControllerTest {
  @Autowired
  private CardController cardController;
  @Autowired
  private WebTestClient webTestClient;
  @MockBean
  private CardService cardService;

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
  @DisplayName("GET -> /api/v1/cards")
  void findAllCard() {
    when(cardService.findAllCard()).thenReturn(Flux.just(cardDto));

    WebTestClient.ResponseSpec responseSpec = webTestClient.get().uri("/api/v1/cards")
      .accept(MediaType.APPLICATION_JSON)
      .exchange();

    responseSpec.expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON);
  }

  @Test
  @DisplayName("GET -> /api/v1/cards/{id}")
  void findOneCard() {
    when(cardService.findByIdCard(id)).thenReturn(Mono.just(cardDto));

    WebTestClient.ResponseSpec responseSpec = webTestClient.get().uri("/api/v1/cards/" + id)
      .accept(MediaType.APPLICATION_JSON)
      .exchange();

    responseSpec.expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON);
  }

  @Test
  @DisplayName("POST -> /api/v1/cards")
  void saveCard() {
    when(cardService.createCard(cardDto)).thenReturn(Mono.just(cardDto));
    Assertions.assertNotNull(cardController.saveCard(cardDto));
  }

  @Test
  @DisplayName("PUT -> /api/v1/cards/{id}")
  void updateCard() {
    when(cardService.updateCard(cardDto, id)).thenReturn(Mono.just(cardDto));
    Assertions.assertNotNull(cardController.updateCard(id, cardDto));
  }

  @Test
  @DisplayName("DELETE -> /api/v1/cards/product/{productId}")
  void removeCardByProduct() {
    when(cardService.removeCardByProductId(productId)).thenReturn(Mono.just(cardDto));

    WebTestClient.ResponseSpec responseSpec = webTestClient.delete().uri("/api/v1/cards/product/" + productId)
      .accept(MediaType.APPLICATION_JSON)
      .exchange();

    responseSpec.expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON);
    responseSpec.expectBody()
      .jsonPath("$.id").isEqualTo(cardDto.getId());
  }

  @Test
  @DisplayName("DELETE -> /api/v1/cards/{id}")
  void removeCard() {
    when(cardService.removeCard(id)).thenReturn(Mono.just(cardDto));

    WebTestClient.ResponseSpec responseSpec = webTestClient.delete().uri("/api/v1/cards/" + id)
      .accept(MediaType.APPLICATION_JSON)
      .exchange();

    responseSpec.expectStatus().isOk()
      .expectHeader().contentType(MediaType.APPLICATION_JSON);
    responseSpec.expectBody()
      .jsonPath("$.id").isEqualTo(cardDto.getId());
  }

}