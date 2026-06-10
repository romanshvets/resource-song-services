CREATE TABLE song
(
    "id"       INTEGER NOT NULL,
    "album"    VARCHAR(255) DEFAULT NULL,
    "artist"   VARCHAR(255) DEFAULT NULL,
    "duration" VARCHAR(255) DEFAULT NULL,
    "name"     VARCHAR(255) DEFAULT NULL,
    "year"     VARCHAR(255) DEFAULT NULL,

    CONSTRAINT song_pkey PRIMARY KEY (id)
);