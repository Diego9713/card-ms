package bootcamp.com.cardms.business.impl;

import static org.mockito.Mockito.when;

import bootcamp.com.cardms.business.helper.CardHelper;
import bootcamp.com.cardms.business.helper.WebClientProductHelper;
import bootcamp.com.cardms.model.Card;
import bootcamp.com.cardms.model.dto.CardDto;
import bootcamp.com.cardms.model.dto.ProductDto;
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

import java.time.LocalDate;
import java.time.LocalDateTime;

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

  private static final ProductDto productDto = new ProductDto();
  private static final String idProduct = "61db64d731dec743727907f3";
  private static final String accountType = "SAVING";
  private static final String accountNumber = "d85c241a-2eb7-40da-938c-097f30d3756f";
  private static final String currency = "PEN";
  private static final double amount = 6300;
  private static final double level = 1;
  private static final double maintenanceCommission = 0;
  private static final LocalDateTime maintenanceCommissionDay = null;
  private static final int maxTransactNumber = 10;
  private static final LocalDate transactNumberDay = null;
  private static final double creditLimit = 0;
  private static final String customer = "61db5ffd7610bd27a53b2b8b";
  private static final String statusProduct = "ACTIVE";
  private static final LocalDate createdAt = LocalDate.now();
  private static final String createdBy = "pedro";
  private static final LocalDate updateAt = LocalDate.now();
  private static final String updateBy = "pedro";
  private static final double minimumAverageAmount = 0;
  private static final double averageDailyBalance = 0;
  private static final LocalDate averageDailyBalanceDay = LocalDate.now();

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

    productDto.setId(idProduct);
    productDto.setAccountType(accountType);
    productDto.setAccountNumber(accountNumber);
    productDto.setCurrency(currency);
    productDto.setAmount(amount);
    productDto.setMaintenanceCommission(maintenanceCommission);
    productDto.setMaintenanceCommissionDay(maintenanceCommissionDay);
    productDto.setMaxTransactNumber(maxTransactNumber);
    productDto.setTransactNumberDay(transactNumberDay);
    productDto.setCreditLimit(creditLimit);
    productDto.setCustomer(customer);
    productDto.setStatus(statusProduct);
    productDto.setCreatedAt(createdAt);
    productDto.setCreatedBy(createdBy);
    productDto.setUpdateAt(updateAt);
    productDto.setUpdateBy(updateBy);
    productDto.setMinimumAverageAmount(minimumAverageAmount);
    productDto.setAverageDailyBalance(averageDailyBalance);
    productDto.setAverageDailyBalanceDay(averageDailyBalanceDay);
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
    when(cardHelper.setObjectCardBySave(card,productDto)).thenReturn(Mono.just(card));
    when(cardRepository.insert(card)).thenReturn(Mono.just(card));
    Assertions.assertNotNull(cardService.createCard(cardDto));
  }

  @Test
  void updateCard() {
    when(cardRepository.findById(id)).thenReturn(Mono.just(card));
    when(cardRepository.findByProductId(productId)).thenReturn(Mono.just(card));
    Card findCard = new Card();
    BeanUtils.copyProperties(card, findCard);
    card.setId(null);
    when(cardHelper.setObjectCardByUpdate(card, findCard)).thenReturn(Mono.just(findCard));
    when(cardRepository.save(findCard)).thenReturn(Mono.just(findCard));
    Assertions.assertNotNull(cardService.updateCard(cardDto, id));
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