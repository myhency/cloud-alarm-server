package cloud.stock.sevenbread.infra;

import cloud.stock.sevenbread.domain.sevenbread.SevenBreadItemHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SevenBreadHistoryRepository extends JpaRepository<SevenBreadItemHistory, Long> {
}
