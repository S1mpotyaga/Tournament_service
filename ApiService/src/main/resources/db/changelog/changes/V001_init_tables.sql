--liquibase formatted sql

--changeset 001:Nex1sG

create type user_role as enum(
    'ADMIN',
    'MANAGER',
    'PARTICIPANT',
    'GUEST'
    );

create table users(
    user_id serial primary key,
    full_name varchar(100) not null,
    role user_role not null default 'GUEST',
    password varchar(255) not null,
    nick varchar(50) not null unique,
    registration_date timestamp not null default now(),
    email varchar(100) not null unique
);


create type tournament_status as enum(
    'REGISTRATION',
    'IN_PROGRESS',
    'FINISHED'
    );

create type tournament_bracket_type as enum (
    'SINGLE_ELIMINATION',
    'DOUBLE_ELIMINATION',
    'ROUND_ROBIN',
    'GROUP_STEP_AND_PLAYOFF'
    );

create table tournament(
    tournament_id serial primary key,
    name varchar(100) not null,
    description text not null,
    bracket_type tournament_bracket_type not null,
    status tournament_status not null default 'REGISTRATION',
    created_date timestamp not null default now()
);

create table tournament_participant (
    id serial primary key,
    user_id integer not null,
    tournament_id integer not null,
    registration_date timestamp not null default now(),
    constraint fk_tp_user
        foreign key (user_id)
            references users(user_id)
                on delete cascade,
    constraint fk_tp_tournament
        foreign key (tournament_id)
            references tournament(tournament_id)
            on delete cascade
);

create type match_status as enum (
    'PENDING',
    'IN_PROGRESS',
    'FINISHED'
    );


create table tournament_match (
    match_id serial primary key,
    participant_id_1 integer not null,
    participant_id_2 integer not null,
    tournament_id integer not null,
    winner_id integer,
    status match_status not null default 'PENDING',

    constraint fk_match_participant1
        foreign key (participant_id_1)
            references tournament_participant(id),

    constraint fk_match_participant2
        foreign key (participant_id_2)
            references tournament_participant(id),

    constraint fk_match_winner
        foreign key (winner_id)
            references tournament_participant(id),

    constraint fk_match_tournament
        foreign key (tournament_id)
            references tournament(tournament_id)
            on delete cascade,

    constraint chk_winner
        check (
            winner_id is null
                or winner_id = participant_id_1
                or winner_id = participant_id_2
            ),

    constraint chk_players
        check (participant_id_1 <> participant_id_2)
);