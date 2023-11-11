CREATE TABLE users (
  id uuid NOT NULL PRIMARY KEY,
  email VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL
);

CREATE TABLE roles (
    id uuid NOT NULL PRIMARY KEY,
    name VARCHAR(36) NOT NULL UNIQUE
);

CREATE TABLE users_roles(
    user_id uuid NOT NULL REFERENCES users (id),
    role_id uuid NOT NULL REFERENCES roles (id)
);

CREATE TABLE authorities (
    id uuid NOT NULL PRIMARY KEY,
    name VARCHAR(36) NOT NULL UNIQUE
);

CREATE TABLE users_authorities(
    user_id uuid NOT NULL REFERENCES users (id),
    authority_id uuid NOT NULL REFERENCES authorities (id)
);

CREATE UNIQUE INDEX users_authorities_idx ON users_authorities (user_id, authority_id);
CREATE UNIQUE INDEX users_roles_idx ON users_roles (user_id, role_id);