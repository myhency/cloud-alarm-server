package cloud.stock.alarm.ui;

import cloud.stock.alarm.app.AlarmService;
import cloud.stock.alarm.domain.Alarm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static cloud.stock.Fixtures.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlarmRestController.class)
@Import(HttpEncodingAutoConfiguration.class)
class AlarmRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlarmService alarmService;

    @Test
    void create() throws Exception {
        //given
        given(alarmService.create(any(Alarm.class))).willReturn(alarmForCreation());

        //when
        final ResultActions resultActions = mockMvc.perform(post("/api/v1/platform/alarm/stockItem")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n" +
                    "  \"itemName\": \"AP시스템\",\n" +
                    "  \"itemCode\": \"265520\",\n" +
                    "  \"recommendPrice\": 30500,\n" +
                    "  \"losscutPrice\": 27800,\n" +
                    "  \"comment\": \"손절 27800\",\n" +
                    "  \"theme\": \"반도체 장비, 플렉서블 디스플레이, LCD장비, OLED(유기 발광 다이오드)\",\n" +
                    "  \"createdAt\": \"2021-05-02T14:53:19\",\n" +
                    "  \"updatedAt\": \"2021-05-02T14:53:19\",\n" +
                    "  \"alarmedAt\": null,\n" +
                    "  \"losscutAt\": null,\n" +
                    "  \"alarmStatus\": null\n" +
                    "}")
            );

        //then
        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    void updateAlarm() throws Exception {
        //given
        given(alarmService.create(any(Alarm.class))).willReturn(alarmForCreation());
        given(alarmService.changeAlarm(any(Long.class),any(Alarm.class))).willReturn(alarmForUpdate());

        //when
        final ResultActions resultActions = mockMvc.perform(put("/api/v1/platform/alarm/stockItem/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n" +
                    "  \"itemName\": \"AP시스템\",\n" +
                    "  \"itemCode\": \"265520\",\n" +
                    "  \"recommendPrice\": 40000,\n" +
                    "  \"losscutPrice\": 30000,\n" +
                    "  \"comment\": \"손절 30000\",\n" +
                    "  \"theme\": \"반도체 장비, 플렉서블 디스플레이, LCD장비, OLED(유기 발광 다이오드)\",\n" +
                    "  \"createdAt\": \"2021-05-02T14:53:19\",\n" +
                    "  \"updatedAt\": \"2021-05-02T14:53:19\",\n" +
                    "  \"alarmedAt\": null,\n" +
                    "  \"losscutAt\": null,\n" +
                    "  \"alarmStatus\": null\n" +
                    "}")
            );

        //then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber());
    }
}