package cloud.stock.stockitem.app;

import cloud.stock.common.EmptyResultDataAccessException;
import cloud.stock.common.ErrorCode;
import cloud.stock.stockitem.domain.ThemeCategory;
import cloud.stock.stockitem.domain.exceptions.AlreadyExistThemeCategoryException;
import cloud.stock.stockitem.infra.ThemeCategoryRepository;
import cloud.stock.stockitem.ui.dto.ThemeCategoryByDateResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Component
public class ThemeCategoryService {

    private final ThemeCategoryRepository themeCategoryRepository;

    public ThemeCategoryService(final ThemeCategoryRepository themeCategoryRepository) {
        this.themeCategoryRepository = themeCategoryRepository;
    }

    @Transactional
    public ThemeCategory create(final ThemeCategory themeCategory) {
        ThemeCategory oldThemeCategory = themeCategoryRepository.findByCategoryCode(themeCategory.getCategoryCode(), themeCategory.getItemCode())
        .orElseGet(() -> themeCategoryRepository.save(themeCategory));

        oldThemeCategory.setTheme(themeCategory.getTheme());
        return themeCategoryRepository.save(oldThemeCategory);
    }

    public List<ThemeCategoryByDateResponse> getCategoryCountByDate(final String dateStr) {
        List<ThemeCategoryByDateResponse> categoryCountByDateList = new ArrayList<>();
        List<Object> obj = (List<Object>)themeCategoryRepository.findThemeCategoriesByDate(dateStr);

        if (obj.size() == 0) {
            throw new EmptyResultDataAccessException(ErrorCode.EMPTY_RESULT);
        }

        obj.stream().forEach((item) -> {
            ThemeCategoryByDateResponse themeCategoryByDateResponse = new ThemeCategoryByDateResponse();
            Object[] arrayList = (Object[])item;
            for(int i = 0; i < arrayList.length; i++) {
                switch (i) {
                    case 0:
                        themeCategoryByDateResponse.setCategoryName((String)arrayList[i]);
                        break;
                    case 1:
                        themeCategoryByDateResponse.setCount(((BigInteger)arrayList[i]).intValue());
                        break;
                    default:
                        break;
                }
            }

            categoryCountByDateList.add(themeCategoryByDateResponse);
        });


        return categoryCountByDateList;
    }

    public List<ThemeCategoryByDateResponse> getCategoryCountByItemCodes(List<String> itemCodes) {
        List<ThemeCategoryByDateResponse> categoryCountByDateList = new ArrayList<>();
        List<Object> obj = (List<Object>)themeCategoryRepository.findThemeCategoriesByItemCodes(itemCodes);

        if (obj.size() == 0) {
            throw new EmptyResultDataAccessException(ErrorCode.EMPTY_RESULT);
        }

        obj.stream().forEach((item) -> {
            ThemeCategoryByDateResponse themeCategoryByDateResponse = new ThemeCategoryByDateResponse();
            Object[] arrayList = (Object[])item;
            for(int i = 0; i < arrayList.length; i++) {
                switch (i) {
                    case 0:
                        themeCategoryByDateResponse.setCategoryName((String)arrayList[i]);
                        break;
                    case 1:
                        themeCategoryByDateResponse.setCount(((BigInteger)arrayList[i]).intValue());
                        break;
                    default:
                        break;
                }
            }

            categoryCountByDateList.add(themeCategoryByDateResponse);
        });


        return categoryCountByDateList;
    }
}
