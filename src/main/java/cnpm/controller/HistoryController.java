package cnpm.controller;

import cnpm.model.CalendarHistory;
import cnpm.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping
    public String getHistory(Model model) {
        List<CalendarHistory> histories = historyService.getAllHistories();
        List<String> accountNames = historyService.getAllAccountNames();
        model.addAttribute("histories", histories);
        model.addAttribute("accountNames", accountNames);
        return "history";
    }
    
    @GetMapping("/search")
    public String searchHistory(@RequestParam(value = "accountName", required = false) String accountName, Model model) {

        List<CalendarHistory> histories;
        List<String> accountNames = historyService.getAllAccountNames();

        histories = historyService.searchHistories(accountName);

        model.addAttribute("histories", histories);
        model.addAttribute("accountNames", accountNames);
        model.addAttribute("selectedAccountName", accountName);
        
        return "history";
    }


}
