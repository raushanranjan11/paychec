package com.thinkss.paycheck.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thinkss.paycheck.entity.GeoLocation;
import com.thinkss.paycheck.repository.GeoLocationRepository;

@RestController
@RequestMapping(value = "/location", produces = MediaType.APPLICATION_JSON_VALUE)
public class BikeAppController {
	
	@Autowired
	GeoLocationRepository geoLocationRepository;
	
	@RequestMapping(value = "/savelocation", method = RequestMethod.POST, consumes = { "application/json" })
	public ResponseEntity<?> saveLocation(@RequestBody GeoLocation geoLocation) {
		
		System.out.println("Location &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&7");
		GeoLocation location = geoLocationRepository.save(geoLocation);
		Map<String, Object> map = new HashMap<String, Object>();
		if(location != null) {
		map.put("status", true);
		map.put("location", location);
		return  ResponseEntity.ok(map);
		}else {
			map.put("status", false);
			return  ResponseEntity.ok(map);
		}
		
	}
	@RequestMapping(value = "/getlocation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getLocation(@RequestBody GeoLocation geoLocation) {
		GeoLocation location = geoLocationRepository.save(geoLocation);
		Map<String, Object> map = new HashMap<String, Object>();
		if(location != null) {
		map.put("status", true);
		map.put("location", location);
		return  ResponseEntity.ok(map);
		}else {
			map.put("status", false);
			return  ResponseEntity.ok(map);
		}
		
	}

}
