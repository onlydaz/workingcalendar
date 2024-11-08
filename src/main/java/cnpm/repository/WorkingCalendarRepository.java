package cnpm.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cnpm.model.WorkingCalendar;

@Repository
public interface WorkingCalendarRepository extends JpaRepository<WorkingCalendar, Long> {
	
	@Query("SELECT w FROM WorkingCalendar w WHERE w.fromDate >= :fromDate AND w.toDate <= :toDate")
	List<WorkingCalendar> findByDateRange(Date fromDate, Date toDate);
}
