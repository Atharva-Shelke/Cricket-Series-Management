package com.cricketSeries.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cricketSeries.dto.SeriesRequestDTO;
import com.cricketSeries.dto.SeriesResponseDTO;
import com.cricketSeries.model.Series;
import com.cricketSeries.utility.SqlLoader;

@Repository
public class SeriesDAO {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private SqlLoader sql;

	public Long insertSeries(SeriesRequestDTO series) {
		String query = sql.get("insert_series");

		MapSqlParameterSource params = new MapSqlParameterSource().addValue("name", series.getName())
				.addValue("location", series.getLocation()).addValue("startDate", series.getStartDate())
				.addValue("endDate", series.getEndDate());

		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(query, params, keyHolder, new String[] { "id" });

		return keyHolder.getKey().longValue();
	}

	public List<Series> getAllSeries() {
		return jdbcTemplate.query(sql.get("get_all_series"), (rs, rowNum) -> {
			Series series = new Series();
			series.setId(rs.getLong("id"));
			series.setName(rs.getString("name"));
			series.setLocation(rs.getString("location"));
			series.setStartDate(rs.getDate("start_date").toLocalDate());
			series.setEndDate(rs.getDate("end_date").toLocalDate());
			return series;
		});

	}

	public SeriesResponseDTO getSeriesById(Long id) {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);

			return jdbcTemplate.queryForObject(sql.get("get_series_by_id"), params, (rs, rowNum) -> {
				SeriesResponseDTO series = new SeriesResponseDTO();
				series.setId(rs.getLong("id"));
				series.setName(rs.getString("name"));
				series.setLocation(rs.getString("location"));
				series.setStartDate(rs.getDate("start_date").toLocalDate());
				series.setEndDate(rs.getDate("end_date").toLocalDate());
				return series;
			});
		} catch (RuntimeException ex) {
			throw new RuntimeException("Series not found with id: " + id);
		}
	}

	public void deleteSeries(Long id) {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
			System.out.println(jdbcTemplate.update(sql.get("delete_series"), params));
		} catch (RuntimeException ex) {
			throw new RuntimeException("Series not found with id: " + id);
		}
	}

	public void updateSeries(Series series) {
		try {
			MapSqlParameterSource params = new MapSqlParameterSource().addValue("name", series.getName())
					.addValue("location", series.getLocation()).addValue("startDate", series.getStartDate())
					.addValue("endDate", series.getEndDate()).addValue("id", series.getId());

			System.out.println(jdbcTemplate.update(sql.get("update_series"), params));
		} catch (RuntimeException ex) {
			throw new RuntimeException("Series not found with id: " + series.getId());
		}
	}
}