package vn.tuan.jobhunter.controller;

import org.springframework.mail.MailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tuan.jobhunter.service.impl.EmailServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class EmailController {
    private final EmailServiceImpl emailService;
    public EmailController(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }
    @GetMapping("/email")
    public String sendEmail() {
        this.emailService.sendEmailSync("tuan.nguyencapoo@hcmut.edu.vn","test send email","<h1><b>Chào bạn</b></h1>",false,true);
        return "ok";
    }

}
