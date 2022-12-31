CREATE TABLE users(
    user_id serial primary key,
    email varchar(80) not null,
    password varchar(255) not null
);