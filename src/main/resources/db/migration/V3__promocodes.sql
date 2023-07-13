CREATE TABLE promo_code
(
    id    UUID PRIMARY KEY,
    value VARCHAR(255) UNIQUE,
    cost  NUMERIC NOT NULL
)