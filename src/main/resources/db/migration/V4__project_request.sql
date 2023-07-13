CREATE TABLE project_request
(
    id            UUID PRIMARY KEY,
    creation_date TIMESTAMP NOT NULL
);

ALTER TABLE project
    ADD COLUMN project_request_id UUID REFERENCES project_request (id) ON DELETE SET NULL;