package com.casestudy.takeaway.eventsvc.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.casestudy.takeaway.eventsvc.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
	public List<Event> findByResourceNameAndResourceIdentifier(String resourceName,Long resourceIdentifier,Sort sort );

}
