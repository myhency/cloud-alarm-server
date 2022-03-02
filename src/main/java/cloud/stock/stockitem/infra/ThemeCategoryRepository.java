package cloud.stock.stockitem.infra;

import cloud.stock.stockitem.domain.ThemeCategory;
import cloud.stock.stockitem.ui.dto.ThemeCategoryByDateResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ThemeCategoryRepository extends JpaRepository<ThemeCategory, Long> {

    @Query(value = "SELECT a " +
            "FROM ThemeCategory a " +
            "WHERE a.categoryCode = :categoryCode " +
            "AND a.itemCode = :itemCode")
    Optional<ThemeCategory> findByCategoryCode(String categoryCode, String itemCode);

    @Query(value = "select b.category_name, count(b.category_name)\n" +
            "from clouddb.stock_item a inner join clouddb.theme_category b \n" +
            "on a.item_code = b.item_code \n" +
            "where a.item_code in (\n" +
            "\tselect item_code \n" +
            "    from clouddb.volume \n" +
            "    where date_format(:dateStr, '%Y-%m-%d') + interval 1 day > created_date\n" +
            "    and created_date > date_format(:dateStr, '%Y-%m-%d')\n" +
            ")\n" +
            "group by b.category_name\n" +
            "order by count(b.category_name) desc\n" +
            "limit 20", nativeQuery = true)
    Collection<Object> findThemeCategoriesByDate(String dateStr);

    @Query(value = "select b.category_name, count(b.category_name)\n" +
            "\tfrom clouddb.stock_item a inner join clouddb.theme_category b\n" +
            "\ton a.item_code = b.item_code\n" +
            "\twhere a.item_code in :itemCodes \n" +
            "\tgroup by b.category_name\n" +
            "\torder by count(b.category_name) desc\n" +
            "\tlimit 20", nativeQuery = true)
    Collection<Object> findThemeCategoriesByItemCodes(@Param("itemCodes") List<String> itemCodes);
}
