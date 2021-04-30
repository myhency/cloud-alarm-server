package stock.alarm.cloud.analyze;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class AnalyzedItemSaveResponse {

    @JsonProperty(value="result")
    private boolean result;
    @JsonProperty(value = "code")
    private String code;
    @JsonProperty(value = "data")
    private Data data;

    class Data {

        @JsonProperty(value = "analyzedItem")
        AnalyzedItem analyzedItem;

        public Data(AnalyzedItem analyzedItem) {
            this.analyzedItem = analyzedItem;
        }
    }

    class AnalyzedItem {
        @JsonProperty(value = "id")
        private Integer id;
        @JsonProperty(value = "itemName")
        private String itemName;
        @JsonProperty(value = "itemCode")
        private String itemCode;

        public AnalyzedItem(Integer id, String itemName, String itemCode) {
            this.id = id;
            this.itemName = itemName;
            this.itemCode = itemCode;
        }
    }

    public AnalyzedItemSaveResponse(boolean result, String code, Integer id, String itemName, String itemCode) {
        this.result = result;
        this.code = code;
        AnalyzedItem analyzedItem = new AnalyzedItem(id, itemName, itemCode);
        this.data = new Data(analyzedItem);
    }
}
