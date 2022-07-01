package cloud.stock.link.infra;

import cloud.stock.link.domain.Link;
import cloud.stock.v2.sevenbread.domain.SevenBreadArchive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findByLinkName(String linkName);
}
