package cloud.stock.v2.sevenbread.infra;

import cloud.stock.v2.sevenbread.domain.SevenBreadArchive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SevenBreadArchiveRepository extends JpaRepository<SevenBreadArchive, Long> {
}
