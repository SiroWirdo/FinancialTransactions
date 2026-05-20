ALTER TABLE user_account
    ADD role VARCHAR(100),
    ADD email VARCHAR(100);

UPDATE user_account
SET role = 'USER'
WHERE role is NULL;

UPDATE user_account
SET email = CONCAT(user_name, '@temp.com')
WHERE email IS NULL;

ALTER TABLE user_account
ALTER COLUMN role SET NOT NULL,
ALTER COLUMN email SET NOT NULL,
ADD CONSTRAINT uk_user_account_email UNIQUE(email);