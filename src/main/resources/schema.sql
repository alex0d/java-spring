create sequence if not exists footballers_seq;
create sequence if not exists teams_seq;
create sequence if not exists users_seq;

create table if not exists footballers (
    id bigint not null primary key,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    team_id bigint
);

create table if not exists teams (
    id bigint not null primary key,
    name varchar(255) not null,
    creation_date date
);

create table if not exists users (
    id bigint,
    username varchar(255),
    password text
);

create table if not exists persistent_logins (
    username varchar(64) not null,
    series varchar(64) not null primary key,
    token varchar(64) not null,
    last_used timestamp not null
);
