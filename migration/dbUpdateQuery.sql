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

