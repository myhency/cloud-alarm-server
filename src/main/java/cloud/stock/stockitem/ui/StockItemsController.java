package cloud.stock.stockitem.ui;

import cloud.stock.common.ErrorCode;
import cloud.stock.common.InvalidParameterException;
import cloud.stock.common.ResponseDto;
import cloud.stock.stockitem.StockItemCreateRequest;
import cloud.stock.stockitem.app.StockItemService;
import cloud.stock.stockitem.domain.StockItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Tag(name = "stock-item", description = "종목 API")
@RestController
@RequestMapping(value = "/api/v1/platform", produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
public class StockItemsController {

    private final StockItemService stockItemService;

    public StockItemsController(StockItemService stockItemService) {
        this.stockItemService = stockItemService;
    }

    @PostMapping(value = "/item/stockItem")
    @Operation(summary = "종목추가", description = "종목명, 종목코드, 테마를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "종목추가 성공",content = @Content(schema = @Schema(implementation = StockItem.class))),
            @ApiResponse(responseCode = "400", description = "종목추가 실패", content = @Content(schema = @Schema(implementation = InvalidParameterException.class)))
    })
    public ResponseEntity createStockItem(@RequestBody @Valid StockItem stockItem) {
        final StockItem created = stockItemService.create(stockItem);
        final URI uri = URI.create("/item/stockItem/"+created.getId());
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping(value = "/item/stockItem/{filterString}")
    @Operation(summary = "종목수정", description = "특정 종목의 종목명 또는 종목코드 또는 테마를 수정합니다.")
    public ResponseEntity modifyStockItem(@PathVariable String filterString, @RequestBody @Valid StockItemCreateRequest stockItemCreateRequest, BindingResult bindingResult) {
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping(value = "/item/stockItem/{filterString}")
    @Operation(summary = "종목삭제", description = "특정 종목을 삭제합니다.")
    public ResponseEntity removeStockItem(@PathVariable String filterString, @RequestBody @Valid StockItemCreateRequest stockItemCreateRequest, BindingResult bindingResult) {
        return ResponseEntity.ok().body(null);
    }

    @GetMapping(value = "/item/stockItem/{filterString}")
    @Operation(summary = "종목조회", description = "특정 종목의 정보를 조회합니다.")
    public ResponseEntity selectStockItem(@PathVariable String filterString) {
        return ResponseEntity.ok().body(null);
    }

    @GetMapping(value = "/item/stockItem/theme/{itemCode}")
    @Operation(summary = "종목테마조회", description = "특정 종목의 테마를 조회합니다.")
    public ResponseEntity<StockItem> selectStockItemTheme(@PathVariable String itemCode) {
        try {
            return ResponseEntity.ok().body(stockItemService.selectThemeByItemCode(itemCode));
        } catch(EmptyResultDataAccessException e) {
            throw new cloud.stock.common.EmptyResultDataAccessException(ErrorCode.EMPTY_RESULT);
        }
    }

    @GetMapping(value = "/item/stockItem")
    @Operation(summary = "종목리스트 조회", description = "모든 종목의 정보를 조회합니다.")
    public ResponseEntity selectStockItems() {
        return ResponseEntity.ok()
                .body(new ResponseDto<>(stockItemService.list()));
    }
}
