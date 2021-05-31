package cloud.stock.alarm.ui;

import cloud.stock.alarm.app.AlarmService;
import cloud.stock.alarm.domain.exceptions.AlreadyExistAlarmException;
import cloud.stock.alarm.ui.dataholder.AlarmDataHolder;
import cloud.stock.alarm.ui.dto.*;
import cloud.stock.common.*;
import cloud.stock.stockitem.domain.exceptions.NotExistStockItemException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.ScopeNotActiveException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import cloud.stock.alarm.domain.Alarm;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@Tag(
        name = "alarm",
        description = "알람 API"
)
@RestController
@RequestMapping(
        value = "/api/v1/platform",
        produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8"
)
public class AlarmRestController {
    private final AlarmService alarmService;

    public AlarmRestController(final AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @PostMapping(value = "/alarm/stockItem")
    @Operation(
            summary = "알람등록",
            description = "특정 종목의 알람설정을 합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "알람등록 성공",
                    content = @Content(
                            schema = @Schema(
                                    implementation = AlarmDataHolder.class
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "알람등록 실패(등록된 알람이 존재합니다.)",
                    content = @Content(
                            schema = @Schema(
                                    implementation = AlreadyExistAlarmException.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "알람등록 실패(종목이 존재하지 않습니다.)",
                    content = @Content(
                            schema = @Schema(
                                    implementation = NotExistStockItemException.class)
                    )
            )
    })
    public ResponseEntity createAlarm(
            @RequestBody AlarmCreationRequestDto alarmCreationRequestDto
    ) throws URISyntaxException {
        final AlarmDataHolder createdAlarm = alarmService.create(
                alarmCreationRequestDto.getItemName(),
                alarmCreationRequestDto.getItemCode(),
                alarmCreationRequestDto.getRecommendPrice(),
                alarmCreationRequestDto.getLosscutPrice(),
                alarmCreationRequestDto.getComment(),
                alarmCreationRequestDto.getTheme()
        );
        return ResponseEntity.created(
                new URI("/alarm/stockItem/"+createdAlarm.getItemCode()))
                .body(new ResponseDto<>(new AlarmCreationResponseDto(
                        createdAlarm.getAlarmId(),
                        createdAlarm.getItemName(),
                        createdAlarm.getItemCode(),
                        createdAlarm.getRecommendPrice(),
                        createdAlarm.getLosscutPrice(),
                        createdAlarm.getComment(),
                        createdAlarm.getAlarmStatus(),
                        createdAlarm.getCreatedDate()))
                );
    }

    @PutMapping(value = "/alarm/stockItem/{alarmId}")
    @Operation(
            summary = "알람수정",
            description = "특정 종목의 알람설정을 수정합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "알람수정 성공",
                    content = @Content(schema = @Schema(
                            implementation = Alarm.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "알람수정 실패",
                    content = @Content(schema = @Schema(
                            implementation = EmptyResultDataAccessException.class)
                    )
            )
    })
    public ResponseEntity updateAlarm(
            @PathVariable final Long alarmId,
            @RequestBody final AlarmModificationRequestDto alarmModificationRequestDto
    ) {
            final AlarmDataHolder modifiedAlarm = alarmService.changeAlarm(
                    alarmId,
                    alarmModificationRequestDto.getItemName(),
                    alarmModificationRequestDto.getItemCode(),
                    alarmModificationRequestDto.getRecommendPrice(),
                    alarmModificationRequestDto.getLosscutPrice(),
                    alarmModificationRequestDto.getComment(),
                    alarmModificationRequestDto.getTheme()
            );

        return ResponseEntity
                .ok(new ResponseDto<>(modifiedAlarm));
    }

    @GetMapping(value = "/alarm/stockItem")
    @Operation(
            summary = "전체알람조회",
            description = "전체 알람을 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "전체알람조회 성공",
                    content = @Content(schema = @Schema(
                            implementation = List.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "전체알람조회 실패",
                    content = @Content(schema = @Schema(
                            implementation = InvalidParameterException.class)
                    )
            )
    })
    public ResponseEntity selectAlarms() {
        return ResponseEntity.ok()
                .body(new ResponseDto<>(alarmService.list()));
    }

    @GetMapping(value = "/alarm/stockItem/{alarmId}")
    @Operation(
            summary = "알람상세조회",
            description = "특정 알람의 상세내용을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "알람상세내용 조회 성공",
                    content = @Content(schema = @Schema(
                            implementation = Alarm.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "알람상세내용 조회 실패",
                    content = @Content(schema = @Schema(
                            implementation = InvalidParameterException.class)
                    )
            )
    })
    public ResponseEntity selectAlarm(@PathVariable Long alarmId) {
        return ResponseEntity.ok()
                .body(new ResponseDto<>(alarmService.getAlarmDetail(alarmId)));
    }

    @GetMapping(value = "/alarm/stockItem/filter")
    @Operation(
            summary = "알람상세조회",
            description = "종목명 또는 종목코드로 특정 알람의 상세내용을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "알람상세내용 조회 성공",
                    content = @Content(schema = @Schema(
                            implementation = Alarm.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "알람상세내용 조회 실패",
                    content = @Content(schema = @Schema(
                            implementation = EmptyResultDataAccessException.class)
                    )
            )
    })
    public ResponseEntity selectAlarmByFilter(
            @RequestParam(value = "itemCode") String itemCode,
            @RequestParam(value = "itemName", required = false) String itemName
    ) {
        return ResponseEntity.ok()
                .body(new ResponseDto<>(alarmService.getAlarmDetailByFilter(itemCode, itemName)));
    }

    @PutMapping(value = "/alarm/stockItem/buy/{alarmId}")
    public ResponseEntity buyAlarm(@PathVariable Long alarmId) {
        final AlarmDataHolder modifiedAlarm = alarmService.updateBuyAlarm(alarmId);

        return ResponseEntity.ok()
                .body(new ResponseDto<>(modifiedAlarm));
    }

    @PutMapping(value = "/alarm/stockItem/losscut/{alarmId}")
    public ResponseEntity losscutAlarm(@PathVariable Long alarmId) {
        final AlarmDataHolder modifiedAlarm = alarmService.updateLosscutAlarm(alarmId);

        return ResponseEntity.ok()
                .body(new ResponseDto<>(modifiedAlarm));
    }
}
