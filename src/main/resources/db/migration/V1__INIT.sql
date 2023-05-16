create table users (
    id serial primary key,
    telegram_token varchar not null
);

create table task (
    id serial primary key,
    user_id bigint references users(id),
    task_name varchar not null,
    description varchar not null,
    task_date date not null,
    count_of_repeat smallint not null,
    is_finish boolean
);

create table event (
    id serial primary key,
    user_id bigint references users(id),
    event_name varchar not null,
    description varchar not null,
    event_date date,
    event_time time,
    count_of_repeat smallint not null,
    is_finish boolean
);

create table notification (
    id serial primary key,
    start_time time not null,
    start_date date,
    count_of_repeat smallint not null,
    task_id bigint references task (id),
    event_id bigint references event(id)
);

create table week_day (
  id serial primary key,
  day_name varchar(20) not null
);

create table week_days_notification (
    notification_id bigint references notification(id),
    week_day_id bigint references week_day(id),
    primary key (week_day_id, notification_id)
);

INSERT INTO week_day (day_name)
VALUES ('Понедельник'),
       ('Вторник'),
       ('Среда'),
       ('Четверг'),
       ('Пятница'),
       ('Суббота'),
       ('Воскресенье');
