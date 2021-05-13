package com.gardenaid.server;

import org.springframework.data.repository.CrudRepository;

import com.gardenaid.server.Plant;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PlantRepository extends CrudRepository<Plant, Integer> {

    public Plant findByName(String name);

}