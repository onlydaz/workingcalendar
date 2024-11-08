package cnpm.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cnpm.model.WorkingCalendar;
import cnpm.service.WorkingCalendarService;

@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    private WorkingCalendarService workingCalendarService;

    @GetMapping
    public String listCalendars(Model model) {
        List<WorkingCalendar> calendars = workingCalendarService.getAllWorkingCalendars();
        model.addAttribute("calendars", calendars);
        return "list";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteCalendars(@PathVariable Long id) {
    	workingCalendarService.deleteWorkingCalendar(id);
    	workingCalendarService.deleteWorkingCalendar(id + 1);
    	
        return "redirect:/view";
    }
    
    @GetMapping("/search")
    public String searchByDateRange(@RequestParam Date fromDate, @RequestParam Date toDate, Model model) {
        List<WorkingCalendar> calendars = workingCalendarService.getWorkingCalendarsByDateRange(fromDate, toDate);
        model.addAttribute("calendars", calendars);
        return "list";
    }

}
