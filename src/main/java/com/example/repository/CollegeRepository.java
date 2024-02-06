package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.College;


@Repository
public interface CollegeRepository extends CrudRepository<College,Long>{

}
