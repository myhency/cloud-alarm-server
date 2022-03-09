package cloud.stock.v2.sevenbread.ui;


import cloud.stock.common.ResponseDto;
import cloud.stock.v2.sevenbread.app.SevenBreadV2Service;
import cloud.stock.v2.sevenbread.ui.dto.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(
        value = "/api/v1/platform",
        produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8"
)
public class SevenBreadV2RestController {
    private final SevenBreadV2Service sevenBreadV2Service;

    public SevenBreadV2RestController(SevenBreadV2Service sevenBreadV2Service) {
        this.sevenBreadV2Service = sevenBreadV2Service;
    }

    @PostMapping(value = "/v2/sevenbread/item")
    public ResponseEntity createSevenBreadItem(
            final @RequestBody @Valid SevenBreadItemCreationRequestDto sevenBreadItemCreationRequestDto)
            throws URISyntaxException {
        Long createdSevenBreadItemId = sevenBreadV2Service.create(
                sevenBreadItemCreationRequestDto
        );

        return ResponseEntity.created(
                new URI("/v2/sevenbread/item/" + createdSevenBreadItemId))
                .body(new ResponseDto<>(createdSevenBreadItemId));
    }

    @GetMapping(value = "/v2/sevenbread/item")
    public ResponseEntity selectSevenBreadItems() {
        return ResponseEntity.ok()
                .body(new ResponseDto<>(sevenBreadV2Service.list()));
    }

    @GetMapping(value = "/v2/sevenbread/item/{itemCode}")
    public ResponseEntity selectSevenBreadItemByItemCode(
            @PathVariable String itemCode
    ) {
        return ResponseEntity.ok()
                .body(new ResponseDto<>(sevenBreadV2Service.getSevenBreadItemByItemCode(itemCode)));
    }

    @PutMapping(value = "/v2/sevenbread/item/{itemCode}")
    public ResponseEntity updateSevenBreadItem(
            @PathVariable String itemCode,
            @RequestBody SevenBreadItemUpdateRequestDto sevenBreadItemUpdateRequestDto
    ) {
        Long id = sevenBreadV2Service.update(
                itemCode,
                sevenBreadItemUpdateRequestDto.getCapturedPrice(),
                sevenBreadItemUpdateRequestDto.getLowestPrice()
        );

        return ResponseEntity.ok()
                .body(id);
    }

    @PutMapping(value = "/v2/sevenbread/item/alarm/{itemCode}/{alarmStatus}")
    public ResponseEntity updateSevenBreadItemAlarmStatus(
            @PathVariable String itemCode,
            @PathVariable String alarmStatus
    ) {
        Long id = sevenBreadV2Service.updateAlarmStatus(
                itemCode,
                alarmStatus
        );

        return ResponseEntity.ok()
                .body(id);
    }

    @PutMapping(value = "/v2/sevenbread/item/daily/chart")
    public ResponseEntity updateSevenBreadDailyChartTrace(
            final @RequestBody @Valid SevenBreadDailyChartTraceRequestDto sevenBreadDailyChartTraceRequestDto
    ) {
        Long id = sevenBreadV2Service.upsertSevenBreadDailyChartTrace(sevenBreadDailyChartTraceRequestDto);

        return ResponseEntity.ok()
                .body(id);
    }

    @PutMapping(value = "/v2/sevenbread/item/daily/buying")
    public ResponseEntity updateSevenBreadDailyBuyingTrace(
            final @RequestBody @Valid SevenBreadDailyBuyingTraceRequestDto sevenBreadDailyBuyingTraceRequestDto
    ) {
        Long id = sevenBreadV2Service.upsertSevenBreadDailyBuyingTrace(sevenBreadDailyBuyingTraceRequestDto);
        return ResponseEntity.ok()
                .body(id);
    }

    @PutMapping(value = "/v2/sevenbread/item/trace")
    public ResponseEntity updateSevenBreadItemInTrace(
            final @RequestBody @Valid SevenBreadItemTraceUpdateRequestDto sevenBreadItemTraceUpdateRequestDto
    ){
        List<Long> result =  sevenBreadV2Service.updateReoccurDateList(sevenBreadItemTraceUpdateRequestDto);
        return ResponseEntity.ok()
                .body(result);
    }

    @PutMapping(value = "/v2/sevenbread/item/archive")
    public ResponseEntity deleteSoldSevenBreadItem(
            final @RequestBody @Valid SevenBreadItemDeleteRequestDto sevenBreadItemDeleteRequestDto
    ) {
        return ResponseEntity.ok()
                .body(sevenBreadV2Service.handleDeleteSold(sevenBreadItemDeleteRequestDto));
    }

    @PutMapping(value = "/v2/sevenbread/item/win")
    public ResponseEntity deleteWinSevenBreadItem(
            final @RequestBody @Valid SevenBreadItemDeleteRequestDto sevenBreadItemDeleteRequestDto
    ) {
        return ResponseEntity.ok()
                .body(sevenBreadV2Service.handleDeleteWin(sevenBreadItemDeleteRequestDto));
    }
}
