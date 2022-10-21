-- USERS
create sequence user_seq start 1 increment 1;

create table users (
    id int8 not null,
    email varchar(255),
    password varchar(255),
    primary key (id)
);

-- ROLES
create sequence role_seq start 1 increment 1;

create table roles (
    id int8 not null,
    name varchar(255),
    primary key (id)
);

-- ROLES IN USER
create table users_roles (
    user_id int8 not null,
    role_id int8 not null
);

alter table if exists users_roles
    add constraint users_roles_fk_role
        foreign key (role_id) references roles;

alter table if exists users_roles
    add constraint users_roles_fk_user
        foreign key (user_id) references users;

-- Cart
create sequence cart_seq start 1 increment 1;

create table carts (
    id int8 not null,
    user_id int8,
    primary key (id)
);

-- LINK BETWEEN CARTS AND USER
alter table if exists carts
    add constraint carts_fk_user
        foreign key (user_id) references users;

-- PRODUCTS
create sequence product_seq start 1 increment 1;

create table products (
    id int8 not null,
    title varchar(255),
    available numeric(19, 2),
    price varchar(255),
    primary key (id)
);

-- PRODUCTS IN CARTS
create table carts_products (
    cart_id int8 not null,
    product_id int8 not null
);

alter table if exists carts_products
    add constraint carts_products_fk_product
        foreign key (product_id) references products;

alter table if exists carts_products
    add constraint carts_products_fk_cart
        foreign key (cart_id) references carts;
