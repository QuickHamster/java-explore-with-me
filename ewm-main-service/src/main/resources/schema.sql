CREATE TABLE IF NOT EXISTS users
(
    user_id bigint generated by default as identity primary key,
    name    varchar(128) NOT NULL,
    email   varchar(128) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS categories
(
    category_id bigint generated by default as identity primary key,
    name        varchar(64) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS locations
(
    location_id bigint generated by default as identity primary key,
    lat         float,
    lon         float
);

CREATE TABLE IF NOT EXISTS events
(
    event_id           bigint generated by default as identity primary key,
    created_on         timestamp without time zone,
    category_id        bigint REFERENCES categories (category_id) NOT NULL,
    annotation         varchar(1024),
    description        varchar(4096),
    confirmed_requests bigint,
    event_date         timestamp without time zone,
    initiator_id       bigint REFERENCES users (user_id) NOT NULL,
    location_id        bigint REFERENCES locations (location_id) NOT NULL,
    paid               boolean,
    participant_limit  int,
    published_on       timestamp without time zone,
    request_moderation boolean,
    state              varchar(64),
    title              varchar(256) NOT NULL
);

CREATE TABLE IF NOT EXISTS requests
(
    request_id   bigint generated by default as identity primary key,
    created      timestamp without time zone,
    event_id     bigint REFERENCES events (event_id) NOT NULL,
    requester_id bigint REFERENCES users (user_id) NOT NULL,
    status       varchar(64)
);

CREATE TABLE IF NOT EXISTS compilations
(
    compilation_id bigint generated by default as identity primary key,
    pinned         boolean,
    title          varchar(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS event_compilation
(
    event_id       bigint REFERENCES events (event_id),
    compilation_id bigint REFERENCES compilations (compilation_id)
);



