package com.mccayl.questionnaire.service;

import com.mccayl.questionnaire.dto.EmailDTO;

public interface EmailService {
    void sendSimpleMail(EmailDTO data);
}
