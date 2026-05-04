package com.cricketSeries.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cricketSeries.dto.MatchRequestDTO;
import com.cricketSeries.dto.MatchResponseDTO;
import com.cricketSeries.dto.SeriesRequestDTO;
import com.cricketSeries.dto.SeriesResponseDTO;
import com.cricketSeries.service.CricketService;

@RestController
@RequestMapping("/cricket")
public class CricketController {

	@Autowired
	private CricketService service;

	@PostMapping("/series")
	public ResponseEntity<Map<String, String>> create(@RequestBody SeriesRequestDTO series) {
		service.createSeries(series);
		return ResponseEntity.ok(Map.of("message", "Series created successfully"));
	}

	@GetMapping("/series")
	public List<SeriesResponseDTO> getAllSeries() {
		return service.getAllSeries();
	}

	@GetMapping("/seriesWithMatches")
	public List<SeriesResponseDTO> getAllSeriesWithMatches() {
		return service.getAllSeriesWithMatches();
	}

	@GetMapping("/series/{id}")
	public SeriesResponseDTO getSeriesById(@PathVariable Long id) {
		return service.getSeriesById(id);
	}

	@GetMapping("/seriesWithMatches/{id}")
	public SeriesResponseDTO getSeriesByIdWithMatches(@PathVariable Long id) {
		return service.getSeriesByIdWithMatches(id);
	}

	@DeleteMapping("/series")
	public ResponseEntity<Map<String, String>> deleteSeries(@RequestParam Long id) {
		service.deleteSeries(id);
		return ResponseEntity.ok(Map.of("message", "Series deleted successfully"));
	}

	@PutMapping("/series/{id}")
	public ResponseEntity<Map<String, String>> update(@PathVariable Long id, @RequestBody SeriesRequestDTO dto) {

		service.updateSeries(id, dto);

		return ResponseEntity.ok(Map.of("message", "Series updated successfully"));
	}

	@PostMapping("/match/{id}")
	public ResponseEntity<Map<String, String>> createMatch(@PathVariable Long id, @RequestBody MatchRequestDTO match) {
		service.createMatch(id, match);
		return ResponseEntity.ok(Map.of("message", "Match created successfully in series " + id));
	}

	@GetMapping("/matches/{id}")
	public List<MatchResponseDTO> getAllMatchesBySeriesId(@PathVariable Long id) {
		return service.getAllMatchesBySeriesId(id);
	}

	@GetMapping("/match/{id}")
	public MatchResponseDTO getMatchById(@PathVariable Long id) {
		return service.getMatchById(id);
	}

	@DeleteMapping("/match")
	public ResponseEntity<Map<String, String>> deleteMatch(@RequestParam Long id) {
		service.deleteMatch(id);
		return ResponseEntity.ok(Map.of("message", "Match id " + id + " deleted successfully"));
	}

	@PutMapping("/match/{id}")
	public ResponseEntity<Map<String, String>> updateMatch(@PathVariable Long id, @RequestBody MatchRequestDTO match) {
		service.updateMatch(id, match);
		return ResponseEntity.ok(Map.of("message", "Match id " + id + " updated successfully"));
	}

}
