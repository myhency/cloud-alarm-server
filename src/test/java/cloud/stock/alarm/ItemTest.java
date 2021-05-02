package cloud.stock.alarm;

import org.junit.jupiter.api.Test;
import cloud.stock.alarm.domain.Item;

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