CREATE TABLE tickets
(
    id          uuid                NOT NULL PRIMARY KEY,
    user_id     uuid                 NOT NULL REFERENCES users (id),
    title       VARCHAR(255)                NOT NULL,
    description TEXT                        NOT NULL,
    status      VARCHAR(20)                 NOT NULL,
    created_at  TIMESTAMP(3) WITH TIME ZONE NOT NULL DEFAULT current_timestamp(3),
    updated_at  TIMESTAMP(3) WITH TIME ZONE NOT NULL DEFAULT current_timestamp(3)
);

CREATE TABLE attachments
(
    id         uuid                NOT NULL PRIMARY KEY,
    ticket_id  uuid                 NOT NULL REFERENCES tickets (id) ON DELETE CASCADE,
    file_key   TEXT                        NOT NULL,
    created_at TIMESTAMP(3) WITH TIME ZONE NOT NULL DEFAULT current_timestamp(3),
    updated_at TIMESTAMP(3) WITH TIME ZONE NOT NULL DEFAULT current_timestamp(3)
);

CREATE TRIGGER update_tickets_trigger
    BEFORE UPDATE
    ON "tickets"
    FOR EACH ROW
EXECUTE PROCEDURE func_update_time();