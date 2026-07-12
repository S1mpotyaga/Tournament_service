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
    password_hash_code varchar(255) not null,
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
    tournament_name varchar(100) not null,
    tournament_description text not null,
    tournament_bracket_type tournament_bracket_type not null,
    tournament_status tournament_status not null default 'REGISTRATION',
    tournament_max_participants integer not null check (tournament_max_participants > 1),
    registration_start timestamp not null,
    registration_end timestamp not null,
    tournament_start timestamp not null,
    tournament_end timestamp,
    created_at timestamp not null default now(),

    constraint chk_tournament_dates
                       check (
                           registration_start < registration_end
                           and registration_end <= tournament_start
                           and (
                               tournament_end is null
                               or tournament_start < tournament_end
                               )
                           )
);

create type participant_status as enum (
    'PENDING',
    'APPROVED',
    'REJECTED'
    );

create table tournament_participant (
    tournament_participant_id serial primary key,
    user_id integer not null,
    tournament_id integer not null,
    participation_status participant_status not null default 'PENDING',
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
            references users(user_id)
            on delete restrict,
    constraint uq_tournament_user
        unique (user_id, tournament_id)
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
            references tournament_participant(tournament_participant_id),

    constraint fk_match_participant2
        foreign key (participant_id_2)
            references tournament_participant(tournament_participant_id),

    constraint fk_match_winner
        foreign key (winner_id)
            references tournament_participant(tournament_participant_id),

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