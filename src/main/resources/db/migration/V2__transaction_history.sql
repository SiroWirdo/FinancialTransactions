CREATE SEQUENCE transaction_hist_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE transaction_history (
                              id BIGINT PRIMARY KEY DEFAULT nextval('transaction_hist_seq'),
                              from_bank_account_id BIGINT NOT NULL,
                              to_bank_account_id BIGINT NOT NULL,
                              amount NUMERIC(19, 2) NOT NULL DEFAULT 0,
                              type VARCHAR(100) NOT NULL,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              description VARCHAR(255),

                              CONSTRAINT fk_from_bank_account
                                  FOREIGN KEY (from_bank_account_id)
                                      REFERENCES bank_account(id),
                              CONSTRAINT fk_to_bank_account
                                  FOREIGN KEY (to_bank_account_id)
                                  REFERENCES bank_account(id)
);