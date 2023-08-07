package com.mccayl.questionnaire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {
    private String sendTo;
    private String msgBody;
    private String subject;
    private String attachment;
}
