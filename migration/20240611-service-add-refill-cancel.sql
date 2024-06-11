alter table if exists service add column cancel boolean not null default true;
alter table if exists service add column refill boolean not null default false;