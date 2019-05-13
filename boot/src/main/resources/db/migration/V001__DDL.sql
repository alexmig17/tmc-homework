CREATE TABLE User (
    id integer generated by default as identity,
    first_name varchar(255),
    last_name varchar(255),
    email varchar(255),
    password varchar(255),
    role varchar(255),
    primary key (id)
);


CREATE TABLE Product (
    id integer generated by default as identity,
    name varchar(255),
    description clob,
    price decimal,
    image BLOB,
    primary key (id)
);