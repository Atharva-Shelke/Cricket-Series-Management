-- name: insert_series
INSERT INTO series(name, location, start_date, end_date)
VALUES (:name, :location, :startDate, :endDate);

-- name: insert_match
INSERT INTO match(series_id, team_a, team_b, match_date, venue, match_type)
VALUES (:series_id, :team_a, :team_b, :match_date, :venue, :match_type);

-- name: get_all_series
SELECT 
    s.id,
    s.name,
    s.location,
    s.start_date,
    s.end_date,
    json_agg(
        json_build_object(
            'id', m.id,
            'teamA', m.team_a,
            'teamB', m.team_b,
            'matchDate', m.match_date,
            'venue', m.venue,
            'matchType', m.match_type
        )
    ) AS matches
FROM series s
LEFT JOIN match m ON s.id = m.series_id
GROUP BY s.id;

-- name: get_series_by_id
SELECT 
    s.id,
    s.name,
    s.location,
    s.start_date,
    s.end_date,
    json_agg(
        json_build_object(
            'id', m.id,
            'teamA', m.team_a,
            'teamB', m.team_b,
            'matchDate', m.match_date,
            'venue', m.venue,
            'matchType', m.match_type
        )
    ) AS matches
FROM series s
LEFT JOIN match m ON s.id = m.series_id
WHERE s.id = ?
GROUP BY s.id;

-- name: get_series_by_id
SELECT * FROM series WHERE id = :id;

-- name: delete_series
DELETE FROM series WHERE id = ?;

-- name: delete_matches_by_series
DELETE FROM match WHERE series_id = ?;

-- name: update_series
UPDATE series
SET name = ?, location = ?, start_date = ?, end_date = ?
WHERE id = ?;