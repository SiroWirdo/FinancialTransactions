ALTER TABLE deposit
    ADD length_in_months NUMERIC(4,0) NOT NULL DEFAULT 0;

CREATE INDEX idx_deposit_status_maturity
    ON deposit(status, maturity_date);