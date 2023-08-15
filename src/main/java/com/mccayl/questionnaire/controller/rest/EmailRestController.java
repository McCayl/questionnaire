package com.mccayl.questionnaire.controller.rest;

import com.mccayl.questionnaire.dto.EmailDTO;
import com.mccayl.questionnaire.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class EmailRestController {

    private EmailService emailService;

    // Sending a simple Email
    @PostMapping("/sendMail")
    public void
    sendMail(@RequestBody EmailDTO details)
    {
        emailService.sendSimpleMail(details);
    }
}
