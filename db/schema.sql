CREATE TABLE accounts (
                          id serial PRIMARY KEY,
                          fio text NOT NULL,
                          phone text unique NOT NULL
)

CREATE TABLE hall (
                      id serial primary key,
                      place integer unique not null,
                      accounts_id integer references accounts (id) not null
)