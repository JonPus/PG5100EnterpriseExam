create sequence hibernate_sequence start with 1 increment by 1;
create table copy
(
    id                  bigint       not null,
    owned_by_userid     varchar(255) not null,
    item_information_id bigint       not null,
    duplicates             int,
    primary key (id)
);
create table item
(
    id          bigint       not null,
    value       bigint       not null,
    description varchar(255) not null,
    item_name   varchar(255) not null,
    title       varchar(300),
    primary key (id)
);
create table users
(
    userid          varchar(255) not null,
    email           varchar(255),
    enabled         boolean      not null,
    hashed_password varchar(255),
    last_name       varchar(128),
    name            varchar(255),
    currency        bigint,
    available_boxes int,
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
alter table users
    add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);
alter table copy
    add constraint FKex4wa959mkii4fwx3s210ug24 foreign key (owned_by_userid) references users;
alter table copy
    add constraint FK3tv91lsph9bbpk4lpdu231yn0 foreign key (item_information_id) references item;
alter table users_owned_items
    add constraint FKqsn5ohuprxkk1s7cd348e8btl foreign key (owned_items_id) references item;
alter table users_owned_items
    add constraint FK2d6a73d4l6qaq1th7ni5po6rj foreign key (all_owners_userid) references users;
alter table users_roles
    add constraint FKnqgxij5udu4xrsqju9dtbc8pr foreign key (users_userid) references users;
