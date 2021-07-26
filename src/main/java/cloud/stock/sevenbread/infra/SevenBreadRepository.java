package cloud.stock.sevenbread.infra;

import cloud.stock.sevenbread.domain.sevenbread.SevenBreadItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SevenBreadRepository extends JpaRepository<SevenBreadItem, Long> {
    Optional<SevenBreadItem> findByItemCode(String itemCode);

    SevenBreadItem findOneByItemCode(String itemCode);
}
