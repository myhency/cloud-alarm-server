package cloud.stock.v2.sevenbread.infra;

import cloud.stock.v2.sevenbread.domain.SevenBreadDailyTrace;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface SevenBreadDailyTraceRepository extends JpaRepository<SevenBreadDailyTrace, Long> {
    Optional<SevenBreadDailyTrace> findByItemCodeAndClosingDate(String itemCode, String closingDate);

    List<SevenBreadDailyTrace> findByItemCodeAndIsReoccur(String itemCode, Boolean isOccur);

    List<SevenBreadDailyTrace> findAllByItemCode(String itemCode);
}
