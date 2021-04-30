package stock.alarm.cloud.analyze;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class AnalyzedItemTest {

    @Test
    public void builder() {
        AnalyzedItem analyzedItem = AnalyzedItem.builder()
                .itemName("AP시스템")
                .itemCode("265520")
                .recommendPrice(20000)
                .losscutPrice(10000)
                .comment("돌파시 매수하세요")
                .theme("위성")
                .build();

        assertThat(analyzedItem).isNotNull();
    }

}