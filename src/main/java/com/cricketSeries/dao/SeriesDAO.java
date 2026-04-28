package com.cricketSeries.dao;

import java.sql.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cricketSeries.model.Match;
import com.cricketSeries.model.Series;
import com.cricketSeries.utility.SqlLoader;

@Repository
public class SeriesDAO {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private SqlLoader sql;

	public Long insertSeries(Series series) {
		String query = sql.get("insert_series");

		MapSqlParameterSource params = new MapSqlParameterSource().addValue("name", series.getName())
				.addValue("location", series.getLocation()).addValue("startDate", series.getStartDate())
				.addValue("endDate", series.getEndDate());

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(query, params, keyHolder, new String[] { "id" });

		return keyHolder.getKey().longValue();
	}

	public Long insertMatch(Long seriesId, Match match) {
		String query = sql.get("insert_match");

		MapSqlParameterSource params = new MapSqlParameterSource().addValue("series_id", seriesId)
				.addValue("team_a", match.getTeamA()).addValue("team_b", match.getTeamB())
				.addValue("match_date", Date.valueOf(match.getMatchDate())).addValue("venue", match.getVenue())
				.addValue("match_type", match.getMatchType());

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(query, params, keyHolder, new String[] { "id" });

		return keyHolder.getKey().longValue();

	}
//
//    public List<String> getAllSeriesJson() {
//        return jdbcTemplate.query(sql.get("get_all_series"),
//                (rs, rowNum) -> rs.getString("json_agg"));
//    }

	public String getSeriesById(Long id) {
		Map<String, Object> params = Map.of("id", id);

		return jdbcTemplate.queryForObject(sql.get("get_series_by_id"), params, String.class);
	}
//
//    public void deleteSeries(Long id) {
//        jdbcTemplate.update(sql.get("delete_series"), id);
//    }
//
//    public void updateSeries(Series series) {
//        jdbcTemplate.update(sql.get("update_series"),
//                series.getName(),
//                series.getLocation(),
//                Date.valueOf(series.getStartDate()),
//                Date.valueOf(series.getEndDate()),
//                series.getId()
//        );
//    }
}