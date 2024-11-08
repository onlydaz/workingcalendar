package cnpm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cnpm.model.WorkingCalendar;
import cnpm.service.WorkingCalendarService;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private WorkingCalendarService workingCalendarService;
    
    
    @GetMapping
    public String showHomePage(Model model) {
        model.addAttribute("workingCalendar", new WorkingCalendar());
        return "home";
    }
	
    @PostMapping
    public String registerWorkingCalendar(WorkingCalendar calendar, Model model, 
                                          @RequestParam String monAM, @RequestParam String tueAM, 
                                          @RequestParam String wedAM, @RequestParam String thuAM, 
                                          @RequestParam String friAM, @RequestParam String satAM, 
                                          @RequestParam String sunAM,
                                          @RequestParam String monPM, @RequestParam String tuePM, 
                                          @RequestParam String wedPM, @RequestParam String thuPM, 
                                          @RequestParam String friPM, @RequestParam String satPM, 
                                          @RequestParam String sunPM) {

        // AM
        WorkingCalendar amCalendar = new WorkingCalendar();
        amCalendar.setName(calendar.getName());
        amCalendar.setFromDate(calendar.getFromDate());
        amCalendar.setToDate(calendar.getToDate());
        amCalendar.setSection("AM");
        amCalendar.setMon(monAM);
        amCalendar.setTue(tueAM);
        amCalendar.setWed(wedAM);
        amCalendar.setThu(thuAM);
        amCalendar.setFri(friAM);
        amCalendar.setSat(satAM);
        amCalendar.setSun(sunAM);
        amCalendar.setNote(calendar.getNote());
        workingCalendarService.saveWorkingCalendar(amCalendar);

        // PM
        WorkingCalendar pmCalendar = new WorkingCalendar();
        pmCalendar.setName(calendar.getName());
        pmCalendar.setFromDate(calendar.getFromDate());
        pmCalendar.setToDate(calendar.getToDate());
        pmCalendar.setSection("PM");
        pmCalendar.setMon(monPM);
        pmCalendar.setTue(tuePM);
        pmCalendar.setWed(wedPM);
        pmCalendar.setThu(thuPM);
        pmCalendar.setFri(friPM);
        pmCalendar.setSat(satPM);
        pmCalendar.setSun(sunPM);
        pmCalendar.setNote(calendar.getNote());
        workingCalendarService.saveWorkingCalendar(pmCalendar);

        return "home";
    }

}
