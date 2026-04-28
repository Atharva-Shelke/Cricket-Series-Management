CREATE TABLE series (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100),
    start_date DATE,
    end_date DATE
);

CREATE TABLE match (
    id SERIAL PRIMARY KEY,
    series_id INT REFERENCES series(id) ON DELETE CASCADE,
    team_a VARCHAR(50),
    team_b VARCHAR(50),
    match_date DATE,
    venue VARCHAR(100),
    match_type VARCHAR(20),
    CONSTRAINT chk_match_type CHECK (match_type IN ('ODI', 'T20', 'TEST'))
);

CREATE INDEX idx_match_series_id ON match(series_id);