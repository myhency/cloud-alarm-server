package cloud.stock.v2.sevenbread.infra;

import cloud.stock.v2.sevenbread.domain.SevenBreadArchive;
import cloud.stock.v2.sevenbread.domain.SevenBreadWin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SevenBreadWinRepository extends JpaRepository<SevenBreadWin, Long> {
}
