package com.casestudy.takeaway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.casestudy.takeaway.entity.Hobby;
import com.casestudy.takeaway.repository.HobbyRepository;
import com.casestudy.takeaway.service.HobbyService;

@Service
public class HobbyServiceImpl implements HobbyService{
	
	@Autowired
	HobbyRepository hobbyRepository;

	@Transactional(propagation=Propagation.REQUIRED)
	public void loadHobbies(List<Hobby> hobbyList) {
		System.out.println("Loading Data-------");
		hobbyRepository.saveAll(hobbyList);
		
	}

}
