alter table if exists client add column email_password varchar(255);
alter table if exists client add column email varchar(255);
alter table if exists client add column host varchar(255);

UPDATE client SET email_password = '!Wezxro538' WHERE email_password IS NULL;
UPDATE client SET email = 'wezxro@hwalaon.com' WHERE email IS NULL;
UPDATE client SET host = 'smtp.hostinger.com' WHERE host IS NULL;

ALTER TABLE client ALTER COLUMN email SET NOT NULL;
ALTER TABLE client ALTER COLUMN email_password SET NOT NULL;
ALTER TABLE client ALTER COLUMN host SET NOT NULL;