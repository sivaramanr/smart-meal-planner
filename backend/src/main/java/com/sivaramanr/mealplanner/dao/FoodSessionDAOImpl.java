package com.sivaramanr.mealplanner.dao;

import com.sivaramanr.mealplanner.dto.FoodSessionBean;
import com.sivaramanr.mealplanner.entity.FoodSession;
import com.sivaramanr.mealplanner.repository.FoodSessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static java.util.Optional.ofNullable;

@Slf4j
@Component
public class FoodSessionDAOImpl implements FoodSessionDAO {

    @Autowired
    private FoodSessionRepository foodSessionRepository;

    @Override
    public void createSession(FoodSessionBean foodSessionBean) {
        FoodSession foodSession = new FoodSession();
        foodSession.setId(ofNullable(foodSessionBean.getId()).orElseGet(UUID.randomUUID()::toString));
        foodSession.setName(foodSessionBean.getName());
        foodSession.setDescription(foodSessionBean.getDescription());
        foodSession.setTiming(foodSessionBean.getTiming());
        foodSessionRepository.save(foodSession);
        log.debug("Created Session with ID: {}", foodSession.getId());
    }

    @Override
    public Page<FoodSession> getSessions(Integer page, Integer size) {
        final PageRequest pageRequest = PageRequest.of(page, size);
        return foodSessionRepository.findAll(pageRequest);
    }

}
