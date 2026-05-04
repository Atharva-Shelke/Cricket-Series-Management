package com.cricketSeries.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cricketSeries.dao.MatchDAO;
import com.cricketSeries.dao.SeriesDAO;
import com.cricketSeries.dto.MatchRequestDTO;
import com.cricketSeries.dto.MatchResponseDTO;
import com.cricketSeries.dto.SeriesRequestDTO;
import com.cricketSeries.dto.SeriesResponseDTO;
import com.cricketSeries.model.Match;
import com.cricketSeries.model.Series;

@Service
public class CricketService {

	@Autowired
	private SeriesDAO seriesDao;

	@Autowired
	private MatchDAO matchDao;

	public void createSeries(SeriesRequestDTO seriesReq) {
		Series series = new Series();
		series.setName(seriesReq.getName());
		series.setLocation(seriesReq.getLocation());
		series.setStartDate(seriesReq.getStartDate());
		series.setEndDate(seriesReq.getEndDate());

		Long id = seriesDao.insertSeries(series);

		if (id != null) {

			for (MatchRequestDTO m : seriesReq.getMatches()) {
				Match match = new Match();
				match.setTeamA(m.getTeamA());
				match.setTeamB(m.getTeamB());
				match.setMatchDate(m.getMatchDate());
				match.setVenue(m.getVenue());
				match.setMatchType(m.getMatchType());
				matchDao.insertMatch(id, match);
			}
		} else {
			throw new RuntimeException("Series not created: Generated id is null.");
		}

	}

	public List<SeriesResponseDTO> getAllSeries() {

		List<SeriesResponseDTO> seriesRspList = new ArrayList<>();

		for (Series series : seriesDao.getAllSeries()) {
			SeriesResponseDTO seriesRsp = new SeriesResponseDTO();
			seriesRsp.setId(series.getId());
			seriesRsp.setName(series.getName());
			seriesRsp.setLocation(series.getLocation());
			seriesRsp.setStartDate(series.getStartDate());
			seriesRsp.setEndDate(series.getEndDate());

			seriesRspList.add(seriesRsp);
		}
		return seriesRspList;
	}

	public List<SeriesResponseDTO> getAllSeriesWithMatches() {
		return seriesDao.getAllSeriesWithMatches();
	}

	public SeriesResponseDTO getSeriesById(Long id) {
		Series series = seriesDao.getSeriesById(id);
		SeriesResponseDTO seriesRsp = new SeriesResponseDTO();
		seriesRsp.setId(series.getId());
		seriesRsp.setName(series.getName());
		seriesRsp.setLocation(series.getLocation());
		seriesRsp.setStartDate(series.getStartDate());
		seriesRsp.setEndDate(series.getEndDate());
		return seriesRsp;
	}

	public SeriesResponseDTO getSeriesByIdWithMatches(Long id) {
		return seriesDao.getSeriesByIdWithMatches(id);
	}

	public void deleteSeries(Long id) {
		int rows = seriesDao.deleteSeries(id);

		if (rows == 0) {
			throw new RuntimeException("Series not found");
		}
	}

	public void updateSeries(Long id, SeriesRequestDTO seriesReq) {
		Series series = new Series();
		series.setId(id);
		series.setName(seriesReq.getName());
		series.setLocation(seriesReq.getLocation());
		series.setStartDate(seriesReq.getStartDate());
		series.setEndDate(seriesReq.getEndDate());

		int rows = seriesDao.updateSeries(series);

		if (rows == 0) {
			throw new RuntimeException("Series not found");
		}

	}

	public void createMatch(Long seriesId, MatchRequestDTO matchReq) {
		Match match = new Match();
		match.setTeamA(matchReq.getTeamA());
		match.setTeamB(matchReq.getTeamB());
		match.setMatchDate(matchReq.getMatchDate());
		match.setVenue(matchReq.getVenue());
		match.setMatchType(matchReq.getMatchType());
		int rows = matchDao.insertMatch(seriesId, match);

		if (rows == 0) {
			throw new RuntimeException("Match not inserted.");
		}
	}

	public List<MatchResponseDTO> getAllMatchesBySeriesId(Long id) {
		List<MatchResponseDTO> matches = new ArrayList<>();
		for (Match m : matchDao.getAllMatchesBySeriesId(id)) {
			MatchResponseDTO match = new MatchResponseDTO();
			match.setId(m.getId());
			match.setTeamA(m.getTeamA());
			match.setTeamB(m.getTeamB());
			match.setMatchDate(m.getMatchDate());
			match.setVenue(m.getVenue());
			match.setMatchType(m.getMatchType());
			matches.add(match);
		}
		return matches;
	}

	public MatchResponseDTO getMatchById(Long id) {
		MatchResponseDTO matchDto = new MatchResponseDTO();
		Match match = matchDao.getMatchById(id);
		matchDto.setId(match.getId());
		matchDto.setTeamA(match.getTeamA());
		matchDto.setTeamB(match.getTeamB());
		matchDto.setMatchDate(match.getMatchDate());
		matchDto.setVenue(match.getVenue());
		matchDto.setMatchType(match.getMatchType());
		return matchDto;
	}

	public void deleteMatch(Long id) {
		int rows = matchDao.deleteMatch(id);

		if (rows == 0) {
			throw new RuntimeException("Match not found");
		}
	}

	public void updateMatch(Long id, MatchRequestDTO matchReq) {
		Match match = new Match();
		match.setId(id);
		match.setTeamA(matchReq.getTeamA());
		match.setTeamB(matchReq.getTeamB());
		match.setMatchDate(matchReq.getMatchDate());
		match.setVenue(matchReq.getVenue());
		match.setMatchType(matchReq.getMatchType());

		int rows = matchDao.updateMatch(id, match);

		if (rows == 0) {
			throw new RuntimeException("Match not found");
		}
	}
}
