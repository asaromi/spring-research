CREATE DATABASE contacts_api;


CREATE TABLE users
(
    id                  VARCHAR(26) NOT NULL PRIMARY KEY,
    username            VARCHAR(26) NOT NULL,
    name                VARCHAR(26) NOT NULL,
    password            TEXT NOT NULL,
    active_token        TEXT,
    token_expired_at    BIGINT UNSIGNED,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at          TIMESTAMP
);

CREATE TABLE roles
(
    id                  VARCHAR(26) NOT NULL PRIMARY KEY,
    name                VARCHAR(26) NOT NULL,
    description         TEXT,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at          TIMESTAMP
);

CREATE TABLE menus
(
    id                  VARCHAR(26) NOT NULL PRIMARY KEY,
    parent_id           VARCHAR(26),
    name                VARCHAR(64) NOT NULL,
    path                VARCHAR(255) NOT NULL UNIQUE,
    description         TEXT,
    is_active           TINYINT(1) DEFAULT 1,

    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at          TIMESTAMP,

    FOREIGN KEY (parent_id) REFERENCES menus(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE user_roles
(
    id                  VARCHAR(26) NOT NULL PRIMARY KEY,
    user_id             VARCHAR(26),
    role_id             VARCHAR(26),
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE role_accesses
(
    id                  VARCHAR(26) NOT NULL PRIMARY KEY,
    role_id             VARCHAR(26),
    access_id           VARCHAR(26),
    is_active           TINYINT(1) DEFAULT 1,

    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (role_id) REFERENCES roles(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (access_id) REFERENCES menus(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


SELECT * FROM roles;

INSERT INTO menus (id, parent_id, name, path, description)
VALUES ('01JV49QQGDZSSP1E0VR83EVKQF', NULL, 'Search SPAJ', '/dashboard/spaj', 'the root of spaj modules');

SELECT m.* FROM menus m
WHERE (
    :search is null or (
        :search is not null and (
            m.name LIKE CONCAT('%', :search, '%')
                or m.path LIKE CONCAT('%', :search, '%')
                or m.description LIKE CONCAT('%', :search, '%')
            )
        )
    )
  AND (
    :is_active is null or (
        :is_active is not null and (
            m.is_active = :is_active
            )
        )
    )
ORDER BY a.is_active
    LIMIT :offset, :limit
;

SELECT * FROM roles r
ORDER BY r.id DESC
;

INSERT INTO role_accesses (id, role_id, access_id, is_active)
values ('01JV4CD4RPMCT88XWA66VSMD27', '01JTZZ4M3J7BF2SNFWD9Y02V4T', '01JV49QQGDZSSP1E0VR83EVKQF', 1);