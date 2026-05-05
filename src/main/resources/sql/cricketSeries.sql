-- name: insert_series
INSERT INTO series(name, location, start_date, end_date)
VALUES (:name, :location, :startDate, :endDate);

-- name: get_all_series
SELECT id, name, location, start_date, end_date FROM series;

-- name: get_all_series_with_matches
SELECT s.id, s.name, s.location, s.start_date, s.end_date, COALESCE(
	(
	SELECT JSON_AGG(
		JSON_BUILD_OBJECT(
			'id',m.id, 'teamA',m.team_a, 'teamB',m.team_b, 'matchDate',m.match_date, 'venue',m.venue, 'matchType',m.match_type
		) ORDER BY m.match_date 
	) FROM match m where series_id = s.id
),'[]') as matches FROM series s;

-- name: get_series_by_id
SELECT id, name, location, start_date, end_date FROM series WHERE id = :id;

-- name: get_series_by_id_with_matches
SELECT s.id, s.name, s.location, s.start_date, s.end_date, COALESCE(
	(
	SELECT JSON_AGG(
		JSON_BUILD_OBJECT(
			'id',m.id, 'teamA',m.team_a, 'teamB',m.team_b, 'matchDate',m.match_date, 'venue',m.venue, 'matchType',m.match_type
		) ORDER BY m.match_date 
	) FROM match m where series_id = s.id
),'[]') as matches FROM series s WHERE s.id = :id;

-- name: delete_series
DELETE FROM series WHERE id = :id;

-- name: update_series
UPDATE series
SET name = :name, location = :location, start_date = :startDate, end_date = :endDate
WHERE id = :id;

-- name: insert_match
INSERT INTO match(series_id, team_a, team_b, match_date, venue, match_type)
VALUES (:series_id, :team_a, :team_b, :match_date, :venue, :match_type);

-- name: get_all_matches_by_series_id
SELECT id, team_a, team_b, match_date, venue, match_type FROM match where series_id = :id;

-- name: get_match_by_id
SELECT team_a, team_b, match_date, venue, match_type FROM match WHERE id = :id;

-- name: delete_match
DELETE FROM match WHERE id = :id;

-- name: delete_matches_by_series
DELETE FROM match WHERE series_id = :id;

-- name: update_match
UPDATE match
SET team_a = :team_a, team_b = :team_b, match_date = :match_date, venue = :venue, match_type = :match_type
WHERE id = :id;