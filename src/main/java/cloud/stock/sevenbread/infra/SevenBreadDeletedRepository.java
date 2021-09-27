package cloud.stock.sevenbread.infra;

import cloud.stock.sevenbread.domain.sevenbread.SevenBreadDeletedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SevenBreadDeletedRepository extends JpaRepository<SevenBreadDeletedItem, Long> {

    @Query(value = "SELECT a " +
            "FROM SevenBreadDeletedItem a " +
            "WHERE a.reason = 'win'"
    )
    List<SevenBreadDeletedItem> findAllByReason();
}
