package com.cricketSeries.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

	@Transactional
	public long createSeries(SeriesRequestDTO seriesReq) {
		Series series = new Series();
		series.setName(seriesReq.getName());
		series.setLocation(seriesReq.getLocation());
		series.setStartDate(seriesReq.getStartDate());
		series.setEndDate(seriesReq.getEndDate());

		Long seriesId = seriesDao.insertSeries(series);

		if (seriesId == null || seriesId == 0) {
		}

		if (seriesReq.getMatches() != null) {

			for (MatchRequestDTO m : seriesReq.getMatches()) {
				Match match = new Match();
				match.setTeamA(m.getTeamA());
				match.setTeamB(m.getTeamB());
				match.setMatchDate(m.getMatchDate());
				match.setVenue(m.getVenue());
				match.setMatchType(m.getMatchType());
				matchDao.insertMatch(seriesId, match);
			}
		}

		return seriesId;

	}

	public List<SeriesResponseDTO> getAllSeries(boolean includeMatches) {

		if (includeMatches) {
			return seriesDao.getAllSeriesWithMatches();
		}

		List<SeriesResponseDTO> seriesRspList = new ArrayList<>();

		for (Series series : seriesDao.getAllSeries()) {
			seriesRspList.add(map(series));
		}
		return seriesRspList;
	}

	public SeriesResponseDTO getSeriesById(long seriesId, boolean includeMatches) {
		if (includeMatches) {
			return seriesDao.getSeriesByIdWithMatches(seriesId);
		}
		Series series = seriesDao.getSeriesById(seriesId);
		return map(series);
	}

	public void deleteSeries(long seriesId) {
		int rows = seriesDao.deleteSeries(seriesId);

		if (rows == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Series not found");
		}
	}

	public void updateSeries(long seriesId, SeriesRequestDTO seriesReq) {
		Series series = new Series();
		series.setId(seriesId);
		series.setName(seriesReq.getName());
		series.setLocation(seriesReq.getLocation());
		series.setStartDate(seriesReq.getStartDate());
		series.setEndDate(seriesReq.getEndDate());

		int rows = seriesDao.updateSeries(series);

		if (rows == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Series not found");
		}

	}

	public void createMatch(long seriesId, MatchRequestDTO matchReq) {
		Match match = new Match();
		match.setTeamA(matchReq.getTeamA());
		match.setTeamB(matchReq.getTeamB());
		match.setMatchDate(matchReq.getMatchDate());
		match.setVenue(matchReq.getVenue());
		match.setMatchType(matchReq.getMatchType());
		int rows = matchDao.insertMatch(seriesId, match);

		if (rows == 0) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Match not inserted.");
		}
	}

	public List<MatchResponseDTO> getMatchesBySeriesId(long seriesId) {
		List<MatchResponseDTO> matches = new ArrayList<>();
		for (Match match : matchDao.getMatchesBySeriesId(seriesId)) {
			matches.add(map(match));
		}
		return matches;
	}

	public MatchResponseDTO getMatchById(long matchId) {
		Match match = matchDao.getMatchById(matchId);
		return map(match);
	}

	public void deleteMatch(long matchId) {
		int rows = matchDao.deleteMatch(matchId);

		if (rows == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found");
		}
	}

	public void updateMatch(long matchId, MatchRequestDTO matchReq) {
		Match match = new Match();
		match.setId(matchId);
		match.setTeamA(matchReq.getTeamA());
		match.setTeamB(matchReq.getTeamB());
		match.setMatchDate(matchReq.getMatchDate());
		match.setVenue(matchReq.getVenue());
		match.setMatchType(matchReq.getMatchType());

		int rows = matchDao.updateMatch(matchId, match);

		if (rows == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found");
		}
	}

	private SeriesResponseDTO map(Series series) {
		SeriesResponseDTO seriesRsp = new SeriesResponseDTO();
		seriesRsp.setId(series.getId());
		seriesRsp.setName(series.getName());
		seriesRsp.setLocation(series.getLocation());
		seriesRsp.setStartDate(series.getStartDate());
		seriesRsp.setEndDate(series.getEndDate());
		return seriesRsp;
	}

	private MatchResponseDTO map(Match match) {
		MatchResponseDTO matchDto = new MatchResponseDTO();
		matchDto.setId(match.getId());
		matchDto.setTeamA(match.getTeamA());
		matchDto.setTeamB(match.getTeamB());
		matchDto.setMatchDate(match.getMatchDate());
		matchDto.setVenue(match.getVenue());
		matchDto.setMatchType(match.getMatchType());
		return matchDto;
	}
}
