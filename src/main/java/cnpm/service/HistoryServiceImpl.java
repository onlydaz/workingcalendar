package cnpm.service;

import cnpm.model.CalendarHistory;
import cnpm.repository.CalendarHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private CalendarHistoryRepository historyRepository;
    
    @Override
    public List<CalendarHistory> getAllHistories() {
        return historyRepository.findAll();
    }
    
    @Override
    public List<String> getAllAccountNames() {
        return historyRepository.findDistinctNames();
    }

    
    @Override
    public List<CalendarHistory> searchHistories(String accountName) {
        if (accountName != null && !accountName.isEmpty()) {
            // lọc theo tên
            return historyRepository.findByNameContainingIgnoreCase(accountName);
        } else {
            return historyRepository.findAll();
        }
    }
}
