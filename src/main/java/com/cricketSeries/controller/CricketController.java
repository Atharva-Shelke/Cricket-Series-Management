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
	public ResponseEntity<Map<String, Object>> create(@RequestBody SeriesRequestDTO series) {
		long seriesId = service.createSeries(series);
		return ResponseEntity.status(201).body(Map.of("message", "Series created", "seriesId", seriesId));
	}

	@GetMapping("/series")
	public List<SeriesResponseDTO> getAllSeries(@RequestParam(defaultValue = "false") boolean includeMatches) {
		return service.getAllSeries(includeMatches);
	}

	@GetMapping("/series/{seriesId}")
	public SeriesResponseDTO getSeriesById(@PathVariable long seriesId,
			@RequestParam(defaultValue = "false") boolean includeMatches) {
		return service.getSeriesById(seriesId, includeMatches);
	}

	@DeleteMapping("/series/{seriesId}")
	public ResponseEntity<Map<String, String>> deleteSeries(@PathVariable long seriesId) {
		service.deleteSeries(seriesId);
		return ResponseEntity.ok(Map.of("message", "Series " + seriesId + "  deleted successfully"));
	}

	@PutMapping("/series/{seriesId}")
	public ResponseEntity<Map<String, String>> update(@PathVariable long seriesId, @RequestBody SeriesRequestDTO dto) {

		service.updateSeries(seriesId, dto);

		return ResponseEntity.ok(Map.of("message", "Series updated successfully"));
	}

	@PostMapping("/series/{seriesId}/match")
	public ResponseEntity<Map<String, String>> createMatch(@PathVariable long seriesId,
			@RequestBody MatchRequestDTO match) {
		service.createMatch(seriesId, match);
		return ResponseEntity.status(201).body(Map.of("message", "Match created"));
	}

	@GetMapping("/series/{seriesId}/match")
	public List<MatchResponseDTO> getMatchesBySeriesId(@PathVariable long seriesId) {
		return service.getMatchesBySeriesId(seriesId);
	}

	@GetMapping("/match/{matchId}")
	public MatchResponseDTO getMatchById(@PathVariable long matchId) {
		return service.getMatchById(matchId);
	}

	@DeleteMapping("/match/{matchId}")
	public ResponseEntity<Map<String, String>> deleteMatch(@PathVariable long matchId) {
		service.deleteMatch(matchId);
		return ResponseEntity.ok(Map.of("message", "Match id " + matchId + " deleted successfully"));
	}

	@PutMapping("/match/{matchId}")
	public ResponseEntity<Map<String, String>> updateMatch(@PathVariable long matchId,
			@RequestBody MatchRequestDTO match) {
		service.updateMatch(matchId, match);
		return ResponseEntity.ok(Map.of("message", "Match id " + matchId + " updated successfully"));
	}

}
