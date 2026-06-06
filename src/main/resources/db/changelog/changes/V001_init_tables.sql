--liquibase formatted sql

--changeset 001:Nex1sG

create type user_role as enum(
    'admin',
    'organizer',
    'coordinator',
    'participant',
    'guest'
    );

create table users(
    user_id serial primary key,
    full_name varchar(50) not null,
    role user_role not null default 'guest',
    password_hash_code varchar(255) not null,
    nick varchar(50) not null unique,
    registration_date timestamp not null default now(),
    email varchar(100) not null unique
);


create type tournament_status as enum(
    'registration',
    'ongoing',
    'paused',
    'completed',
    'canceled'
    );

create type tournament_bracket_type as enum (
    'single_elimination',
    'double_elimination',
    'round_robin',
    'group_step_and_play_off'
    );

create table tournament(
    tournament_id serial primary key,
    tournament_name varchar(100) not null,
    tournament_description varchar(500) not null,
    tournament_bracket_type tournament_bracket_type not null,
    tournament_status tournament_status not null default 'registration',
    tournament_max_participants integer not null check (tournament_max_participants > 1),
    registration_start timestamp not null,
    registration_end timestamp not null,
    tournament_start timestamp not null,
    tournament_end timestamp,
    created_at timestamp not null default now()
);

create type participant_status as enum (
    'pending',
    'approved',
    'rejected',
    'not_sent'
    );

create table tournament_participant (
    tournament_participant_id serial primary key,
    user_id integer not null,
    tournament_id integer not null,
    participation_status participant_status not null default 'not_sent',
    registration_date timestamp not null default now(),
    created_by integer not null,
    constraint fk_tp_user
        foreign key (user_id)
            references users(user_id)
                on delete cascade,
    constraint fk_tp_tournament
        foreign key (tournament_id)
            references tournament(tournament_id)
            on delete cascade,
    constraint fk_tp_created_by
        foreign key (created_by)
            references users(user_id),
    constraint uq_tournament_user
        unique (user_id, tournament_id)
);

create type match_status as enum (
    'pending',
    'in_progress',
    'finished',
    'cancelled'
    );


create table match (
    match_id serial primary key,
    user_id1 integer not null,
    user_id2 integer not null,
    tournament_id integer not null,
    winner_id integer,
    status match_status not null default 'pending',
    constraint fk_match_user1
        foreign key (user_id1)
            references users(user_id),
    constraint fk_match_user2
        foreign key (user_id2)
            references users(user_id),
    constraint fk_match_winner
        foreign key (winner_id)
            references users(user_id),
    constraint fk_match_tournament
        foreign key (tournament_id)
            references tournament(tournament_id)
            on delete cascade,
    constraint chk_winner
        check (
            winner_id is null
                or winner_id = user_id1
                or winner_id = user_id2
            ),
    constraint chk_players
        check (user_id1 <> user_id2)
);