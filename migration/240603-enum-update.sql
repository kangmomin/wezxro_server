ALTER TABLE account DROP CONSTRAINT account_role_check;
ALTER TABLE account
    ADD CONSTRAINT account_role_check
        CHECK (role IN ('USER', 'ADMIN', 'DEMO'));
ALTER TABLE deposit DROP CONSTRAINT deposit_status_check;
ALTER TABLE deposit
    ADD CONSTRAINT deposit_status_check
        CHECK (status IN ('CANCEL', 'DONE', 'PENDING', 'DELETE'));