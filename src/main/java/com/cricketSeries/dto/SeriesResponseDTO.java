package com.cricketSeries.dto;

import java.time.LocalDate;
import java.util.List;

public class SeriesResponseDTO {

	private Long id;

	private String name;
	private String location;
	private LocalDate startDate;
	private LocalDate endDate;

	private List<MatchDTO> matches;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public List<MatchDTO> getMatches() {
		return matches;
	}

	public void setMatches(List<MatchDTO> matches) {
		this.matches = matches;
	}

}
