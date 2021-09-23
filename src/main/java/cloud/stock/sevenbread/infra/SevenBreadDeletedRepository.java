package cloud.stock.sevenbread.infra;

import cloud.stock.sevenbread.domain.sevenbread.SevenBreadDeletedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SevenBreadDeletedRepository extends JpaRepository<SevenBreadDeletedItem, Long> {
}
