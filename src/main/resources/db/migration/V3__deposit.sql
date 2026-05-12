CREATE SEQUENCE deposit_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE deposit (
    id BIGINT PRIMARY KEY DEFAULT nextval('deposit_seq'),
    source_bank_account BIGINT NOT NULL,
    amount NUMERIC(19,2) NOT NULL DEFAULT 0,
    type VARCHAR(100) NOT NULL,
    rate NUMERIC(3,2) NOT NULL DEFAULT 0,
    maturity_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(100) NOT NULL,

    CONSTRAINT fk_source_bank_account
             FOREIGN KEY (source_bank_account)
                     REFERENCES bank_account(id)
);