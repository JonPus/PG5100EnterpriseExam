create sequence hibernate_sequence start with 1 increment by 1;
create table copy
(
    id                  bigint       not null,
    item_information_id bigint       not null,
    owned_by_userid     varchar(255) not null,
    primary key (id)
);
create table item
(
    id             bigint       not null,
    description    varchar(255),
    item_name      varchar(300) not null,
    title          varchar(300),
    value          bigint       not null,
    number_of_item integer      not null,
    primary key (id)
);
create table users
(
    userid          varchar(255) not null,
    available_boxes integer      not null,
    currency        bigint,
    email           varchar(255),
    enabled         boolean      not null,
    hashed_password varchar(255) not null,
    last_name       varchar(128),
    name            varchar(128),
    owned_items     integer,
    primary key (userid)
);
create table users_owned_items
(
    all_owners_userid varchar(255) not null,
    owned_items_id    bigint       not null
);
create table users_roles
(
    users_userid varchar(255) not null,
    roles        varchar(255)
);

-- This code is added and edited from Andrea Arcuri's github repository - https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/backend/src/main/resources/db/migration/V1.0__createDB.sql'
alter table users
    add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);
alter table copy
    add constraint FKsadw2gxrjmsdpwwauacde7n1d foreign key (item_information_id) references item;
alter table copy
    add constraint FKqn21pe3mrcdrybw4nrvrox6u9 foreign key (owned_by_userid) references users;
alter table users_owned_items
    add constraint FKp0cfga0rr2lp2dj17c4vru2r3 foreign key (owned_items_id) references item;
alter table users_owned_items
    add constraint FKaoxcfn72t7avmn1g2u9abu2mj foreign key (all_owners_userid) references users;
alter table users_roles
    add constraint FKnqgxij5udu4xrsqju9dtbc8pr foreign key (users_userid) references users