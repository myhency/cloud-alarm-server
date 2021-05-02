package stock.alarm.cloud.analyze;

import org.junit.jupiter.api.Test;
import stock.alarm.cloud.analyze.domain.Item;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    @Test
    public void builder() {
        Item item = Item.builder()
                .itemName("AP시스템")
                .itemCode("265520")
                .build();

        assertThat(item).isNotNull();
    }

}