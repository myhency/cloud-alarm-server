package cloud.stock.stockitem.ui;

import cloud.stock.common.ResponseDto;
import cloud.stock.stockitem.app.ThemeCategoryService;
import cloud.stock.stockitem.domain.ThemeCategory;
import cloud.stock.stockitem.ui.dto.ThemeCategoryByItemCodesRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@Tag(name = "theme-category", description = "테마 카테고리 API")
@RestController
@RequestMapping(value = "/api/v1/platform", produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
@Slf4j
public class ThemeCategoryController {

    private final ThemeCategoryService themeCategoryService;

    public ThemeCategoryController(ThemeCategoryService themeCategoryService) {
        this.themeCategoryService = themeCategoryService;
    }

    @PostMapping(value = "/item/theme/category")
    @Operation(summary = "테마 카테고리 추가", description = "테마 카테고리를 추가합니다.")
    public ResponseEntity createThemeCategory(@RequestBody @Valid ThemeCategory themeCategory) {
        final ThemeCategory created = themeCategoryService.create(themeCategory);
        final URI uri = URI.create("/item/theme/category" + created.getId());
        return ResponseEntity.created(uri).body(created);
    }

    @GetMapping(value = "/item/theme/category/{dateStr}")
    @Operation(summary = "날짜별 테마 카테고리 조회", description = "유통주식수대비 거래량 기준으로 가장 많이 출몰한 상위 10개 테마를 조회할 수 있습니다.")
    public ResponseEntity selectCategoryCountByDate(@PathVariable String dateStr) {
        return ResponseEntity.ok()
                .body(new ResponseDto<>(themeCategoryService.getCategoryCountByDate(dateStr)));
    }

    @PostMapping(value = "/item/theme/category/realtime")
    @Operation(summary = "실시간 테마 카테고리 조회", description = "유통주식수대비 거래량 기준으로 가장 많이 출몰한 상위 10개 테마를 실시간으로 조회할 수 있습니다.")
    public ResponseEntity selectCategoryCountByItemCodes(@RequestBody ThemeCategoryByItemCodesRequest themeCategoryByItemCodesRequest) {
//        log.debug(themeCategoryByItemCodesRequest.getItemCodes().get(0));
        return ResponseEntity.ok()
                .body(new ResponseDto<>(themeCategoryService.getCategoryCountByItemCodes(themeCategoryByItemCodesRequest.getItemCodes())));
    }
}
