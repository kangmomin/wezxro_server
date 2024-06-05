ALTER TABLE account ALTER COLUMN name TYPE varchar(20);
ALTER TABLE account ALTER COLUMN password TYPE TEXT;
ALTER TABLE account ALTER COLUMN email TYPE varchar(200);

ALTER TABLE category ALTER COLUMN name TYPE varchar(200);

ALTER TABLE deposit ALTER COLUMN name TYPE varchar(100);
ALTER TABLE deposit ALTER COLUMN business_email TYPE varchar(100);
ALTER TABLE deposit ALTER COLUMN business_name TYPE varchar(50);
ALTER TABLE deposit ALTER COLUMN business_regno TYPE varchar(500);
ALTER TABLE deposit ALTER COLUMN note TYPE varchar(50);

ALTER TABLE providers ALTER COLUMN description TYPE TEXT;
ALTER TABLE providers ALTER COLUMN api_key TYPE TEXT;
ALTER TABLE providers ALTER COLUMN api_url TYPE TEXT;

ALTER TABLE service ALTER COLUMN name TYPE TEXT;
ALTER TABLE service ALTER COLUMN description TYPE TEXT;
