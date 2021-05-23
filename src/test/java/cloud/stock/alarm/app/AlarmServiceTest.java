package cloud.stock.alarm.app;

import cloud.stock.alarm.domain.Alarm;
import cloud.stock.alarm.domain.AlarmRepository;
import cloud.stock.alarm.domain.strategy.AlarmStatus;
import cloud.stock.alarm.infra.AlarmDao;
import cloud.stock.alarm.ui.dataholder.AlarmDataHolder;
import cloud.stock.alarm.ui.dto.AlarmCreationRequestDto;
import cloud.stock.stockitem.domain.StockItem;
import cloud.stock.stockitem.domain.StockItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlarmServiceTest {

    @InjectMocks
    private AlarmService alarmService;

    @Mock
    private AlarmRepository alarmRepository;

    @Mock
    private StockItemRepository stockItemRepository;

    private Long alarmId = 1L;
    private String itemName = "AP시스템";
    private String itemCode = "265520";
    private Integer recommendPrice = 30500;
    private Integer losscutPrice = 27800;
    private String comment = "손절 27800";
    private String theme = "반도체 장비, 플렉서블 디스플레이, LCD장비, OLED(유기 발광 다이오드)";

    private Alarm mockNormalAlarm = Alarm.builder()
            .alarmId(alarmId)
            .itemName(itemName)
            .itemCode(itemCode)
            .recommendPrice(recommendPrice)
            .losscutPrice(losscutPrice)
            .comment(comment)
            .theme(theme)
            .alarmStatus(AlarmStatus.ALARM_CREATED)
            .build();

    private StockItem mockNormalStockItem = StockItem.builder()
            .itemName(itemName)
            .itemCode(itemCode)
            .theme(theme)
            .build();

    private Alarm newAlarm = Alarm.builder()
            .itemName(itemName)
            .itemCode(itemCode)
            .recommendPrice(recommendPrice)
            .losscutPrice(losscutPrice)
            .comment(comment)
            .theme(theme)
            .build();


    @DisplayName("알람 등록 성공")
    @Test
    public void alarm_Created_Success() {
        AlarmCreationRequestDto dto = AlarmCreationRequestDto.builder()
                .itemName(itemName)
                .itemCode(itemCode)
                .recommendPrice(recommendPrice)
                .losscutPrice(losscutPrice)
                .comment(comment)
                .theme(theme)
                .build();
        given(stockItemRepository.findByItemCode(itemCode))
                .willReturn(Optional.of(mockNormalStockItem));
        given(alarmRepository.findByItemCode(itemCode))
                .willReturn(Collections.emptyList());
        given(alarmRepository.save(any(Alarm.class))).willReturn(mockNormalAlarm);

        AlarmDataHolder alarmDataHolder = alarmService.create(
                itemName,
                itemCode,
                recommendPrice,
                losscutPrice,
                comment,
                theme
        );

        assertThat(alarmDataHolder.getAlarmId()).isEqualTo(alarmId);
        assertThat(alarmDataHolder.getAlarmStatus()).isEqualTo(AlarmStatus.ALARM_CREATED.name());
    }

    @DisplayName("알람 수정 성공")
    @Test
    void alarm_Modified_Success() {

    }
}