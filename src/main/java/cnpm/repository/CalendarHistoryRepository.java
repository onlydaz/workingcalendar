package cnpm.repository;

import cnpm.model.CalendarHistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarHistoryRepository extends JpaRepository<CalendarHistory, Long> {
	
	@Query("SELECT DISTINCT h.name FROM CalendarHistory h")
    List<String> findDistinctNames();

	List<CalendarHistory> findByNameContainingIgnoreCase(String accountName);

}
