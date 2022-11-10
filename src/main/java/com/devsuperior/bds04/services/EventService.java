package com.devsuperior.bds04.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.repositories.EventRepository;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository repository;

	@Autowired
	private CityRepository cityRepository;

	@Transactional
	public EventDTO insert(EventDTO dto) {

		Event entity = new Event();

		entity.setName(dto.getName());
		entity.setDate(dto.getDate());
		entity.setUrl(dto.getUrl());

		Optional<City> objCity = cityRepository.findById(dto.getCityId());
		City city = objCity.orElseThrow(() -> new ResourceNotFoundException("City not found"));

		entity.setCity(city);

		entity = repository.save(entity);

		return new EventDTO(entity);
	}

	public Page<EventDTO> findAll(Pageable pegeable) {

		Page<Event> result = repository.findAll(pegeable);
		Page<EventDTO> page = result.map(x -> new EventDTO(x));

		return page;
	}
}
