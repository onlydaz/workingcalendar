package cnpm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cnpm.model.CalendarHistory;
import cnpm.model.WorkingCalendar;
import cnpm.repository.CalendarHistoryRepository;
import cnpm.repository.WorkingCalendarRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkingCalendarServiceImpl implements WorkingCalendarService {

    @Autowired
    private WorkingCalendarRepository repository;
    
    @Autowired
    private CalendarHistoryRepository historyRepository;
       
    @Override
    public void saveWorkingCalendar(WorkingCalendar calendar) {
        repository.save(calendar);
    }

    @Override
    public List<WorkingCalendar> getAllWorkingCalendars() {
        return repository.findAll();
    }
    
    @Override
    public WorkingCalendar getWorkingCalendarById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void updateWorkingCalendar(WorkingCalendar calendar) {
        WorkingCalendar existingCalendar = repository.findById(calendar.getId()).orElse(null);

        if (existingCalendar != null) {

            repository.save(calendar);
        }
    }
    
    @Override
    public List<WorkingCalendar> getWorkingCalendarsByDateRange(java.sql.Date fromDate, java.sql.Date toDate) {
        return repository.findByDateRange(fromDate, toDate);
    }
    
    @Override
    public void deleteWorkingCalendar(Long id) {
    	WorkingCalendar calendarToDelete = repository.findById(id).orElse(null);
    	saveHistory(calendarToDelete, "BEFORE DELETE");
    	repository.deleteById(id);
    }
    
    private void saveHistory(WorkingCalendar calendar, String action) {
        CalendarHistory history = new CalendarHistory();
        
        history.setWorkingCalendarId(calendar.getId());
        history.setFromDate(calendar.getFromDate());
        history.setToDate(calendar.getToDate());
        history.setName(calendar.getName());
        history.setSection(calendar.getSection());
        history.setNote(calendar.getNote());
        
        history.setMon(calendar.getMon());
        history.setTue(calendar.getTue());
        history.setWed(calendar.getWed());
        history.setThu(calendar.getThu());
        history.setFri(calendar.getFri());
        history.setSat(calendar.getSat());
        history.setSun(calendar.getSun());
        
        history.setAction(action);
        history.setChangedAt(LocalDateTime.now());
        
        historyRepository.save(history);
    }
    
    
}
