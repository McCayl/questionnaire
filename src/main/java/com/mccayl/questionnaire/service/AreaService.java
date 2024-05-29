package com.mccayl.questionnaire.service;


import com.mccayl.questionnaire.model.Area;

public interface AreaService {
    Area getAreaByName(String name);
    Area getAreaById(Long id);
}
