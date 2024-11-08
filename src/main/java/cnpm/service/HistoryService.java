package cnpm.service;

import cnpm.model.CalendarHistory;

import java.util.List;

public interface HistoryService {
	List<CalendarHistory> getAllHistories();

	List<String> getAllAccountNames();

	List<CalendarHistory> searchHistories(String accountName);

}
