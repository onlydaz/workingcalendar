package cnpm.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cnpm.model.CalendarHistory;
import cnpm.model.WorkingCalendar;
import cnpm.repository.CalendarHistoryRepository;
import cnpm.service.WorkingCalendarService;

@Controller
@RequestMapping("/view/edit")
public class EditController {

    @Autowired
    private WorkingCalendarService workingCalendarService;
    
    @Autowired
    private CalendarHistoryRepository historyRepository;

    @GetMapping("/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        WorkingCalendar calendarAM = workingCalendarService.getWorkingCalendarById(id);
        WorkingCalendar calendarPM = workingCalendarService.getWorkingCalendarById(id + 1);

        model.addAttribute("calendarAM", calendarAM);
        model.addAttribute("calendarPM", calendarPM);

        return "edit";
    }
    
    @PostMapping("/{id}")
    public String updateWorkingCalendar(@PathVariable Long id, @RequestParam String name1, 
                                        @RequestParam String fromDate1, @RequestParam String toDate1, 
                                        @RequestParam String monAM, @RequestParam String tueAM, 
                                        @RequestParam String wedAM, @RequestParam String thuAM, 
                                        @RequestParam String friAM, @RequestParam String satAM, 
                                        @RequestParam String sunAM, @RequestParam String monPM, 
                                        @RequestParam String tuePM, @RequestParam String wedPM, 
                                        @RequestParam String thuPM, @RequestParam String friPM, 
                                        @RequestParam String satPM, @RequestParam String sunPM, 
                                        @RequestParam String note1, Model model) {
        // AM
        WorkingCalendar amCalendar = workingCalendarService.getWorkingCalendarById(id);
        saveHistory(amCalendar, "BEFORE UPDATE");
        amCalendar.setName(name1);
        amCalendar.setFromDate(java.sql.Date.valueOf(fromDate1));
        amCalendar.setToDate(java.sql.Date.valueOf(toDate1));
        
        amCalendar.setMon(monAM);
        amCalendar.setTue(tueAM);
        amCalendar.setWed(wedAM);
        amCalendar.setThu(thuAM);
        amCalendar.setFri(friAM);
        amCalendar.setSat(satAM);
        amCalendar.setSun(sunAM);
        
        amCalendar.setNote(note1);

        // PM
        WorkingCalendar pmCalendar = workingCalendarService.getWorkingCalendarById(id + 1);
        saveHistory(pmCalendar, "BEFORE UPDATE");
        pmCalendar.setName(name1); 
        pmCalendar.setFromDate(java.sql.Date.valueOf(fromDate1)); 
        pmCalendar.setToDate(java.sql.Date.valueOf(toDate1));
        
        pmCalendar.setMon(monPM);
        pmCalendar.setTue(tuePM);
        pmCalendar.setWed(wedPM);
        pmCalendar.setThu(thuPM);
        pmCalendar.setFri(friPM);
        pmCalendar.setSat(satPM);
        pmCalendar.setSun(sunPM);
        
        pmCalendar.setNote(note1);

        workingCalendarService.updateWorkingCalendar(amCalendar);
        workingCalendarService.updateWorkingCalendar(pmCalendar);

        return "redirect:/view";
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
        
        // (UPDATE, DELETE)
        history.setAction(action);
        history.setChangedAt(LocalDateTime.now());
        
        historyRepository.save(history);
    }
}
