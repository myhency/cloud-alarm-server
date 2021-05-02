package cloud.stock.alarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AlarmControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("알람등록의 정상적인 생성 테스트")
    public void createAnalyzedItem() throws Exception {
        AnalyzedItemSaveRequest analyzedItem = AnalyzedItemSaveRequest.builder()
                .itemName("AP시스템")
                .itemCode("265520")
                .recommendPrice(20000)
                .losscutPrice(10000)
                .comment("돌파하면 매수하세요")
                .theme("위성")
                .build();

        mockMvc.perform(post("/api/v1/platform/alarm/analyzedItem")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(analyzedItem)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.analyzedItem.id").exists());
    }

    @Test
    @DisplayName("알람등록시 requestbody의 비정상요청 테스트")
    public void invalid_RequestBody_createAnalyzedItem() throws Exception {
        AnalyzedItemSaveRequest analyzedItem = AnalyzedItemSaveRequest.builder()
                .itemName("AP시스템")
                .recommendPrice(20000)
                .losscutPrice(10000)
                .comment("돌파하면 매수하세요")
                .theme("위성")
                .build();

        mockMvc.perform(post("/api/v1/platform/alarm/analyzedItem")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(analyzedItem)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}