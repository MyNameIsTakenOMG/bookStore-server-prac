create table book(
    id uuid not null primary key,
    title varchar not null,
    description varchar not null,
    release_year int not null
)