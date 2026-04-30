package com.cricketSeries.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cricketSeries.dto.MatchDTO;
import com.cricketSeries.model.Match;
import com.cricketSeries.utility.SqlLoader;

@Repository
public class MatchDAO {
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private SqlLoader sql;

	public Long insertMatch(Long seriesId, MatchDTO match) {
		String query = sql.get("insert_match");

		MapSqlParameterSource params = new MapSqlParameterSource().addValue("series_id", seriesId)
				.addValue("team_a", match.getTeamA()).addValue("team_b", match.getTeamB())
				.addValue("match_date", Date.valueOf(match.getMatchDate())).addValue("venue", match.getVenue())
				.addValue("match_type", match.getMatchType());

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(query, params, keyHolder, new String[] { "id" });

		return keyHolder.getKey().longValue();

	}

	public List<Match> getAllMatchesBySeriesId(Long id) {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
			return jdbcTemplate.query(sql.get("get_all_matches_by_series_id"), params, (rs, rowNum) -> {
				Match match = new Match();
				match.setTeamA(rs.getString("team_a"));
				match.setTeamB(rs.getString("team_b"));
				match.setMatchDate((LocalDate) rs.getObject("match_date"));
				match.setVenue(rs.getString("venue"));
				match.setMatchType(rs.getString("match_type"));
				return match;
			});
		} catch (RuntimeException ex) {
			throw new RuntimeException("Series not found with id: " + id);
		}

	}

	public Match getMatchById(Long id) {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);

			return jdbcTemplate.queryForObject(sql.get("get_match_by_id"), params, (rs, rowNum) -> {
				Match match = new Match();
				match.setTeamA(rs.getString("team_a"));
				match.setTeamB(rs.getString("team_b"));
				match.setMatchDate((LocalDate) rs.getObject("match_date"));
				match.setVenue(rs.getString("venue"));
				match.setMatchType(rs.getString("match_type"));
				return match;
			});
		} catch (RuntimeException ex) {
			throw new RuntimeException("Match not found with id: " + id);
		}
	}

	public void deleteMatch(Long id) {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
			jdbcTemplate.update(sql.get("delete_match"), params);
		} catch (RuntimeException ex) {
			throw new RuntimeException("Match not found with id: " + id);
		}
	}

	public void updateMatch(Match match) {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", match.getId())
					.addValue("team_a", match.getTeamA()).addValue("team_b", match.getTeamB())
					.addValue("match_date", Date.valueOf(match.getMatchDate())).addValue("venue", match.getVenue())
					.addValue("match_type", match.getMatchType());

			jdbcTemplate.update(sql.get("update_match"), params);
		} catch (RuntimeException ex) {
			throw new RuntimeException("Match not found with id: " + match.getId());
		}
	}
}
