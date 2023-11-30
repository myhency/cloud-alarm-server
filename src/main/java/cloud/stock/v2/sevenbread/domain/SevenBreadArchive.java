package cloud.stock.v2.sevenbread.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class SevenBreadArchive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "item_code", nullable = false)
    private String itemCode;
    private Integer closingPrice;
    private Integer highestPrice;
    private Integer lowestPrice;
    private Integer wBuyAmount; //외인순매수
    private Integer gBuyAmount; //기관순매수
    private Integer pBuyAmount; //개인순매수
    private Boolean isReoccur;  // Y & N
    private String tradingState; // Win : 15% 이상 달성, Lose : 대량매도/기준일 저점 갱신, On : 거래중
    @Column(name = "closing_date", nullable = false)
    private String closingDate;
    private String winDate;
    private String loseDate;
}
