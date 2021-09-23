package cloud.stock.sevenbread.ui;

import cloud.stock.common.ResponseDto;
import cloud.stock.sevenbread.app.SevenBreadDeletedService;
import cloud.stock.sevenbread.app.SevenBreadHistoryService;
import cloud.stock.sevenbread.app.SevenBreadService;
import cloud.stock.sevenbread.infra.SevenBreadRepository;
import cloud.stock.sevenbread.ui.dataholder.SevenBreadDeletedItemDataHolder;
import cloud.stock.sevenbread.ui.dataholder.SevenBreadItemDataHolder;
import cloud.stock.sevenbread.ui.dataholder.SevenBreadItemHistoryDataHolder;
import cloud.stock.sevenbread.ui.dto.SevenBreadItemCreationRequestDto;
import cloud.stock.sevenbread.ui.dto.SevenBreadItemHistoryRequestDto;
import cloud.stock.sevenbread.ui.dto.SevenBreadItemUpdateRequestDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Tag(
        name = "sevenbread",
        description = "007빵 API"
)
@RestController
@RequestMapping(
        value = "/api/v1/platform",
        produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8"
)
public class SevenBreadRestController {
    private final SevenBreadService sevenBreadService;
    private final SevenBreadHistoryService sevenBreadHistoryService;
    private final SevenBreadDeletedService sevenBreadDeletedService;

    public SevenBreadRestController(SevenBreadService sevenBreadService,
                                    SevenBreadHistoryService sevenBreadHistoryService,
                                    SevenBreadDeletedService sevenBreadDeletedService
    ) {
        this.sevenBreadService = sevenBreadService;
        this.sevenBreadHistoryService = sevenBreadHistoryService;
        this.sevenBreadDeletedService = sevenBreadDeletedService;
    }

    @PostMapping(value = "/sevenbread/item")
    public ResponseEntity createSevenBreadItem(
            @RequestBody SevenBreadItemCreationRequestDto sevenBreadItemCreationRequestDto
    ) throws URISyntaxException {
        final SevenBreadItemDataHolder createdSevenBreadItem = sevenBreadService.create(
                sevenBreadItemCreationRequestDto.getItemName(),
                sevenBreadItemCreationRequestDto.getItemCode(),
                sevenBreadItemCreationRequestDto.getCapturedDate(),
                sevenBreadItemCreationRequestDto.getMajorHandler(),
                sevenBreadItemCreationRequestDto.getTheme()
        );

        return ResponseEntity.created(
                new URI("/sevenbread/item" + createdSevenBreadItem.getId()))
                .body(new ResponseDto<>(createdSevenBreadItem));
    }

    @PutMapping(value = "/sevenbread/item/{itemCode}")
    public ResponseEntity updateSevenBreadItem(
            @PathVariable String itemCode,
            @RequestBody SevenBreadItemUpdateRequestDto sevenBreadItemUpdateRequestDto
    ) {
        final SevenBreadItemDataHolder updatedSevenBreadItem = sevenBreadService.update(
                itemCode,
                sevenBreadItemUpdateRequestDto.getCapturedPrice(),
                sevenBreadItemUpdateRequestDto.getClosingPrice(),
                sevenBreadItemUpdateRequestDto.getFluctuationRate(),
                sevenBreadItemUpdateRequestDto.getPriceByYesterday(),
                sevenBreadItemUpdateRequestDto.getVolume()
        );

        return ResponseEntity.ok()
                .body(new ResponseDto<>(updatedSevenBreadItem.getId()));
    }

    @PutMapping(value = "/sevenbread/item/today/{itemCode}")
    public ResponseEntity updateSevenBreadItemToday(
            @PathVariable String itemCode,
            @RequestBody SevenBreadItemUpdateRequestDto sevenBreadItemUpdateRequestDto
    ) {
        final SevenBreadItemDataHolder updatedSevenBreadItem = sevenBreadService.updateToday(
                itemCode,
                sevenBreadItemUpdateRequestDto.getClosingPrice(),
                sevenBreadItemUpdateRequestDto.getFluctuationRate(),
                sevenBreadItemUpdateRequestDto.getPriceByYesterday(),
                sevenBreadItemUpdateRequestDto.getVolume()
        );

        return ResponseEntity.ok()
                .body(new ResponseDto<>(updatedSevenBreadItem.getId()));
    }

    @GetMapping(value = "/sevenbread/item")
    public ResponseEntity selectSevenBreadItems() {
        return ResponseEntity.ok()
                .body(new ResponseDto<>(sevenBreadService.list()));
    }

    @GetMapping(value = "/sevenbread/item/detail")
    public ResponseEntity selectSevenBreadItemByItemCode(
            @RequestParam(value = "itemCode") String itemCode
    ) {
        return ResponseEntity.ok()
                .body(new ResponseDto<>(sevenBreadService.getSevenBreadItemDetailByItemCode(itemCode)));
    }

    @DeleteMapping(value = "/sevenbread/item/{id}/{action}")
    public ResponseEntity deleteSevenBreadItems(
            @PathVariable Long id,
            @PathVariable String action
    ) {
        final SevenBreadItemDataHolder deletedSevenBreadItem = sevenBreadService.delete(id);

        final SevenBreadDeletedItemDataHolder sevenBreadDeletedItemDataHolder = sevenBreadDeletedService.create(
                deletedSevenBreadItem.getItemName(),
                deletedSevenBreadItem.getItemCode(),
                deletedSevenBreadItem.getCapturedPrice(),
                deletedSevenBreadItem.getCapturedDate(),
                deletedSevenBreadItem.getMajorHandler(),
                action
        );

        return ResponseEntity.ok().body(new ResponseDto<>(sevenBreadDeletedItemDataHolder));
    }

    @PostMapping(value = "/sevenbread/item/history")
    public ResponseEntity createSevenBreadItemHistory(
            @RequestBody SevenBreadItemHistoryRequestDto sevenBreadItemHistoryRequestDto
    ) throws URISyntaxException {
        final SevenBreadItemHistoryDataHolder createdSevenBreadItemHistory = sevenBreadHistoryService.create(
                sevenBreadItemHistoryRequestDto.getItemName(),
                sevenBreadItemHistoryRequestDto.getItemCode(),
                sevenBreadItemHistoryRequestDto.getStartingPrice(),
                sevenBreadItemHistoryRequestDto.getHighestPrice(),
                sevenBreadItemHistoryRequestDto.getLowestPrice(),
                sevenBreadItemHistoryRequestDto.getClosingPrice(),
                sevenBreadItemHistoryRequestDto.getCapturedDate(),
                sevenBreadItemHistoryRequestDto.getCapturedPrice()
        );

        return ResponseEntity.created(
                new URI("/sevenbread/item/history" + createdSevenBreadItemHistory.getId()))
                .body(new ResponseDto<>(createdSevenBreadItemHistory));
    }
}
