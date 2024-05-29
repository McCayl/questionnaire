package com.mccayl.questionnaire.service.impl;

import com.mccayl.questionnaire.model.Area;
import com.mccayl.questionnaire.repo.AreaRepository;
import com.mccayl.questionnaire.service.AreaService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {
    private final AreaRepository repository;

    @Override
    public Area getAreaByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Area getAreaById(Long id) {
        return repository.findById(id).orElseThrow();
    }
}
