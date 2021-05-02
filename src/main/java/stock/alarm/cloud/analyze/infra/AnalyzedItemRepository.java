package stock.alarm.cloud.analyze.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import stock.alarm.cloud.analyze.domain.AnalyzedItem;

public interface AnalyzedItemRepository extends JpaRepository<AnalyzedItem, Integer> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE AnalyzedItem a SET a.comment = :comment, a.recommendPrice = :recommendPrice, a.losscutPrice = :losscutPrice WHERE a.id = :id")
    int updateAnalyzedItem(Integer id, int recommendPrice, int losscutPrice, String comment);

}
