alter table if exists account rename column "createdAt" to "created_at";
alter table if exists account rename column "updatedAt" to "updated_at";
alter table if exists account add column static_rate bigint not null default 0;
ALTER TABLE account ALTER COLUMN static_rate DROP DEFAULT;
-- 위제로 clientId를 기본으로 적용.
alter table if exists account add column client_id uuid not null default 'e218d032-148d-409b-8056-a34e48267fd5';
ALTER TABLE account ALTER COLUMN client_id DROP DEFAULT;


alter table if exists category rename column "createdAt" to "created_at";
alter table if exists category rename column "updatedAt" to "updated_at";
alter table if exists category add column client_id uuid not null default 'e218d032-148d-409b-8056-a34e48267fd5';
ALTER TABLE category ALTER COLUMN client_id DROP DEFAULT;

alter table if exists service rename column "createdAt" to "created_at";
alter table if exists service rename column "updatedAt" to "updated_at";
alter table if exists service add column client_id uuid not null default 'e218d032-148d-409b-8056-a34e48267fd5';
ALTER TABLE service ALTER COLUMN client_id DROP DEFAULT;

create table "order" (order_id bigserial not null, created_at timestamp(6) not null, updated_at timestamp(6) not null, answer_number bigint, api_order_id bigint not null, comments TEXT, comments_custom_package TEXT, count bigint not null, groups TEXT, hashtag TEXT, hashtags TEXT, link varchar(255), media_url TEXT, remain bigint not null, service_id bigint not null, start_cnt bigint not null, status varchar(255) not null check (status in ('COMPLETED','CANCELED','PROCESSING','INPROGRESS','PARTIALS','PENDING')), total_charge float(53) not null, type varchar(255) not null check (type in ('DEFAULT','SUBSCRIPTION','CUSTOM_COMMENTS','CUSTOM_COMMENTS_PACKAGE','MENTIONS_WITH_HASHTAGS','MENTIONS_CUSTOM_LIST','MENTIONS_HASHTAG','MENTIONS_USER_FOLLOWER','MENTIONS_MEDIEA_LIKERS','PACKAGE','COMMENT_LIKES','POLL','COMMENTS_REPLIES','INVITES_FROM_GROUPS')), user_id bigint not null, username TEXT, usernames TEXT, primary key (order_id))

