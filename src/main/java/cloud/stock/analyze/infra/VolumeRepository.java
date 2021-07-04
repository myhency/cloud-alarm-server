package cloud.stock.analyze.infra;

import cloud.stock.analyze.domain.volume.Volume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface VolumeRepository extends JpaRepository<Volume, Long> {

    @Query(value = "SELECT distinct date_format(created_date, '%Y-%m-%d') FROM volume ORDER BY 1 DESC", nativeQuery = true)
    Collection<String> findAllDates();

    List<Volume> findAllByCreatedDateBetween(LocalDateTime from, LocalDateTime to);
}
