package cnpm.service;

import java.sql.Date;
import java.util.List;

import cnpm.model.WorkingCalendar;

public interface WorkingCalendarService {

	void saveWorkingCalendar(WorkingCalendar calendar);

	List<WorkingCalendar> getAllWorkingCalendars();

	WorkingCalendar getWorkingCalendarById(Long id);

	void updateWorkingCalendar(WorkingCalendar calendar);

	void deleteWorkingCalendar(Long id);

	List<WorkingCalendar> getWorkingCalendarsByDateRange(Date fromDate, Date toDate);

}
