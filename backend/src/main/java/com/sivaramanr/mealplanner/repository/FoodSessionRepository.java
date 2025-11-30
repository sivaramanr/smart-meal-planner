package com.sivaramanr.mealplanner.repository;

import com.sivaramanr.mealplanner.entity.FoodSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FoodSessionRepository extends JpaRepository<FoodSession, String>, JpaSpecificationExecutor<FoodSession> {
}
