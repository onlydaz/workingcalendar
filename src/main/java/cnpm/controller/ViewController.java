package cnpm.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
        model.addAttribute("fromDate", fromDate);
        model.addAttribute("toDate", toDate);
        return "list";
    }
    
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportToExcel(@RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate) throws IOException {
        
        List<WorkingCalendar> calendars;

        // Chuyển từ chuỗi sang Date nếu có giá trị từDate và toDate
        Date from = parseDate(fromDate);
        Date to = parseDate(toDate);

        if (from != null && to != null) {
            calendars = workingCalendarService.getWorkingCalendarsByDateRange(from, to);
        } else {
            calendars = workingCalendarService.getAllWorkingCalendars();
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Working Calendar");

            // Tạo kiểu ô cho hàng tiêu đề
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
            XSSFFont headerFont = ((XSSFWorkbook) workbook).createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            // Hàng tiêu đề
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Name", "From Date", "To Date", "Section", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun", "Note"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Tạo kiểu ô cho các dòng dữ liệu
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);
            dataStyle.setAlignment(HorizontalAlignment.CENTER);

            // Các hàng dữ liệu
            int rowNum = 1;
            for (WorkingCalendar calendar : calendars) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(calendar.getId());
                row.createCell(1).setCellValue(calendar.getName());
                row.createCell(2).setCellValue(calendar.getFromDate().toString());
                row.createCell(3).setCellValue(calendar.getToDate().toString());
                row.createCell(4).setCellValue(calendar.getSection());
                row.createCell(5).setCellValue(calendar.getMon());
                row.createCell(6).setCellValue(calendar.getTue());
                row.createCell(7).setCellValue(calendar.getWed());
                row.createCell(8).setCellValue(calendar.getThu());
                row.createCell(9).setCellValue(calendar.getFri());
                row.createCell(10).setCellValue(calendar.getSat());
                row.createCell(11).setCellValue(calendar.getSun());
                row.createCell(12).setCellValue(calendar.getNote());

                // Áp dụng kiểu dữ liệu cho từng ô
                for (int i = 0; i < headers.length; i++) {
                    row.getCell(i).setCellStyle(dataStyle);
                }
            }

            // Điều chỉnh độ rộng cột để phù hợp với nội dung
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Xuất file Excel dưới dạng byte array
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            byte[] bytes = out.toByteArray();

            // Thiết lập header cho phản hồi
            HttpHeaders headers1 = new HttpHeaders();
            headers1.setContentDispositionFormData("attachment", "WorkingCalendar.xlsx");
            headers1.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .headers(headers1)
                    .body(bytes);
        }
    }

    // Phương thức chuyển đổi từ String sang Date
    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = sdf.parse(dateStr);
            return new Date(parsedDate.getTime());
        } catch (Exception e) {
            return null;
        }
    }

}
