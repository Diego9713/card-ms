package bootcamp.com.cardms.business.helper;

import bootcamp.com.cardms.model.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WebClientProductHelper {
  @Autowired
  private WebClient webClient;

  /**
   * Method to change product status from card.
   *
   * @param id -> is the product identifier.
   * @return a boolean variable.
   */
  public Mono<Boolean> deleteProduct(String id) {
    Mono<ProductDto> productDtoMono = webClient.delete()
        .uri("/api/v1/products/" + id)
        .retrieve()
        .bodyToMono(ProductDto.class);
    return productDtoMono.flatMap(cardDto -> cardDto.getId() != null ? Mono.just(true) : Mono.just(false));

  }

}
