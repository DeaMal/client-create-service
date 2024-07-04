create schema if not exists shop;

create table if not exists shop.client (
    id serial primary key,
    firstname varchar(40) not null,
    lastname varchar(40) not null,
    phone varchar(18) not null,
    phone_extra varchar(18),
    email varchar(40) not null unique,
    address varchar(200),
    create_at timestamp not null,
    updated_at timestamp,
    notes varchar(1000),
    last_order_date date,
    total_orders int,
    total_spent money
);