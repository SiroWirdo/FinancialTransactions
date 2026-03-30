CREATE SEQUENCE user_account_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE user_account (
                              id BIGINT PRIMARY KEY DEFAULT nextval('user_account_seq'),
                              user_name VARCHAR(100) NOT NULL UNIQUE,
                              first_name VARCHAR(100) NOT NULL,
                              last_name VARCHAR(100) NOT NULL,
                              password VARCHAR(255) NOT NULL
);

CREATE SEQUENCE bank_account_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE bank_account (
                              id BIGINT PRIMARY KEY DEFAULT nextval('bank_account_seq'),
                              user_id BIGINT NOT NULL,
                              bank_account_number VARCHAR(34) NOT NULL UNIQUE,
                              balance NUMERIC(19, 2) NOT NULL DEFAULT 0,

                              CONSTRAINT fk_bank_account_user
                                  FOREIGN KEY (user_id)
                                      REFERENCES user_account(id)
                                      ON DELETE CASCADE
);

CREATE SEQUENCE iban_seq START WITH 1 INCREMENT BY 1;