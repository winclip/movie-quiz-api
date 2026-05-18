ALTER TABLE clients
    ADD COLUMN nickname VARCHAR(32);

UPDATE clients
SET nickname = (
    (ARRAY[
        'CineFan',
        'ReelFan',
        'MovieBuff',
        'FilmFan',
        'QuizStar',
        'SceneHero',
        'PopcornPro',
        'SilverReel',
        'FrameFan',
        'ReelHero',
        'CinemaGo',
        'StarReel',
        'QuizReel',
        'FilmQuiz',
        'MovieMind'
    ])[1 + (abs(hashtext(id::text)) % 15)]
    || '_' || lower(left(replace(id::text, '-', ''), 8))
)
WHERE nickname IS NULL;

ALTER TABLE clients
    ALTER COLUMN nickname SET NOT NULL;

ALTER TABLE clients
    ADD CONSTRAINT uq_clients_nickname UNIQUE (nickname);
