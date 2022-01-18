package bootcamp.com.cardms.business.impl;

import static org.mockito.Mockito.when;

import bootcamp.com.cardms.business.helper.CardHelper;
import bootcamp.com.cardms.business.helper.WebClientProductHelper;
import bootcamp.com.cardms.model.Card;
import bootcamp.com.cardms.model.CardDto;
import bootcamp.com.cardms.repository.ICardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
class CardServiceTest {
  @Autowired
  private CardService cardService;
  @MockBean
  private ICardRepository cardRepository;
  @MockBean
  private CardHelper cardHelper;
  @MockBean
  private WebClientProductHelper webClientProductHelper;

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
  void findAllCard() {
    when(cardRepository.findAll()).thenReturn(Flux.just(card));
    Assertions.assertNotNull(cardService.findAllCard());
  }

  @Test
  void findByIdCard() {
    when(cardRepository.findById(id)).thenReturn(Mono.just(card));
    Assertions.assertNotNull(cardService.findByIdCard(id));
  }

  @Test
  void createCard() {
    when(cardRepository.findByProductId(productId)).thenReturn(Mono.just(card));
    when(cardHelper.setObjectCardBySave(card)).thenReturn(Mono.just(card));
    when(cardRepository.insert(card)).thenReturn(Mono.just(card));
    Assertions.assertNotNull(cardService.createCard(cardDto));
  }

  @Test
  void updateCard() {
    when(cardRepository.findById(id)).thenReturn(Mono.just(card));
    when(cardRepository.findByProductId(productId)).thenReturn(Mono.just(card));
    Card findCard = new Card();
    BeanUtils.copyProperties(card,findCard);
    card.setId(null);
    when(cardHelper.setObjectCardByUpdate(card,findCard)).thenReturn(Mono.just(findCard));
    when(cardRepository.save(findCard)).thenReturn(Mono.just(findCard));
    Assertions.assertNotNull(cardService.updateCard(cardDto,id));
  }

  @Test
  void removeCardByProductId() {
    when(cardRepository.findByProductId(productId)).thenReturn(Mono.just(card));
    Assertions.assertNotNull(cardService.removeCardByProductId(productId));
  }

  @Test
  void removeCard() {
    when(cardRepository.findById(id)).thenReturn(Mono.just(card));
    when(webClientProductHelper.deleteProduct(productId)).thenReturn(Mono.just(true));
    Assertions.assertNotNull(cardService.removeCard(id));
  }
}