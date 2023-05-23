create table jwt_token
(
    id            serial primary key,
    user_id       bigint references users (id) not null,
    refresh_token varchar(255)                 not null
);