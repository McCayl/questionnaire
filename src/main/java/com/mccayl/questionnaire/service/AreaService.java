package com.mccayl.questionnaire.service;


import com.mccayl.questionnaire.model.Area;

import java.util.List;

public interface AreaService {
    List<Area> getAll();
    Area getAreaByName(String name);
    Area getAreaById(Long id);
}
