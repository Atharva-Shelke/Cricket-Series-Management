package com.cricketSeries.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cricketSeries.model.Series;
import com.cricketSeries.model.Match;
import com.cricketSeries.service.CricketService;

@RestController
@RequestMapping("/cricketSeries")
public class CricketController {

	@Autowired
	private CricketService service;

	@PostMapping
	public ResponseEntity<String> create(@RequestBody Series series) {
		service.createSeries(series);
		return ResponseEntity.status(HttpStatus.CREATED).body("Created");
	}

	@PostMapping("/match")
	public ResponseEntity<String> createMatch(Long id, @RequestBody Match match) {
		service.createMatch(id, match);
		return ResponseEntity.status(HttpStatus.CREATED).body("Created");
	}

	@GetMapping
	public String getSeriesById(Long id) {
		return service.getSeriesById(id);
	}
}
