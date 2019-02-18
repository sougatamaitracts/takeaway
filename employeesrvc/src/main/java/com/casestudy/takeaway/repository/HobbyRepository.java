package com.casestudy.takeaway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casestudy.takeaway.entity.Hobby;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Long>{

}
