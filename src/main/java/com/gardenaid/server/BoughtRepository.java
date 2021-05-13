package com.gardenaid.server;

import org.springframework.data.repository.CrudRepository;

import com.gardenaid.server.Bought;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BoughtRepository extends CrudRepository<Bought, Integer> {

    public Iterable<Bought> findByUserId(Integer userId);
}