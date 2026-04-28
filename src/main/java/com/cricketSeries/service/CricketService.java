package com.cricketSeries.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cricketSeries.dao.SeriesDAO;
import com.cricketSeries.model.Match;
import com.cricketSeries.model.Series;

@Service
public class CricketService {

    @Autowired
    private SeriesDAO dao;

    public void createSeries(Series series) {
        Long id = dao.insertSeries(series);

//        for (Match m : series.getMatches()) {
//            dao.insertMatch(id, m);
//        }
    }
    
    public String getSeriesById(Long id) {
    	return dao.getSeriesById(id);
    }
    
    public void createMatch(Long seriesId, Match match) {
    	dao.insertMatch(seriesId, match);
    }
}
