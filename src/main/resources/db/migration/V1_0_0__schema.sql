create schema if not exists shop;

create table if not exists shop.client (
    id serial primary key not null,
    firstname varchar(40) not null,
    lastname varchar(40) not null,
    phone varchar(12) not null,
    phone_extra varchar(12),
    email varchar(40) not null,
    address varchar(200),
    create_at timestamp not null,
    unique (firstname, lastname)
);