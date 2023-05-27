alter table users
drop column telegram_token ;

alter table users
add column telegram_token varchar;