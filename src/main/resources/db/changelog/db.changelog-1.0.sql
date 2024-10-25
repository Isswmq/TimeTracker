--liquibase formatted sql

--changeset isswmq:1
CREATE TABLE IF NOT EXISTS users(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(64),
    role VARCHAR(32) NOT NULL
);

--changeset isswmq:2
CREATE TABLE IF NOT EXISTS refresh_token(
    username VARCHAR(255) PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    expiry_date TIMESTAMP NOT NULL
);

--changeset isswmq:3
CREATE TABLE IF NOT EXISTS project(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    owner_id BIGINT NOT NULL,
    CONSTRAINT fk_project_owner FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);

--changeset isswmq:4
CREATE TABLE IF NOT EXISTS user_project (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    project_id INT NOT NULL,
    CONSTRAINT fk_user_project_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_project_project FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
);

--changeset isswmq:5
CREATE TABLE IF NOT EXISTS task (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    project_id INT NOT NULL,
    assigned_to BIGINT,
    created_by BIGINT NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING' NOT NULL,
    CONSTRAINT fk_task_project FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE,
    CONSTRAINT fk_task_assigned_to FOREIGN KEY (assigned_to) REFERENCES users(id) ON DELETE SET NULL,
    CONSTRAINT fk_task_created_by FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE CASCADE
);

--changeset isswmq:6
CREATE TABLE IF NOT EXISTS task_session (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    task_id BIGINT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    pause_start_time TIMESTAMP,
    pause_end_time TIMESTAMP,
    end_time TIMESTAMP,
    status VARCHAR(50) DEFAULT 'IN_PROGRESS' NOT NULL,
    CONSTRAINT fk_task_session_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_task_session_task FOREIGN KEY (task_id) REFERENCES task(id) ON DELETE CASCADE
);

CREATE INDEX idx_task_assigned_to ON task(assigned_to);
CREATE INDEX idx_task_created_by ON task(created_by);
CREATE INDEX idx_task_project ON task(project_id);




