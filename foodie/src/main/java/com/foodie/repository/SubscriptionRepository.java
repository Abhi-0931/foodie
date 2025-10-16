package com.foodie.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodie.model.EnumCollections.SubscriptionPlan;
import com.foodie.model.Subscription;
//import com.foodie.model.SubscriptionPlan;


public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByPlan(SubscriptionPlan plan);
    Optional<Subscription> findByVendor_Email(String email);  
}
 