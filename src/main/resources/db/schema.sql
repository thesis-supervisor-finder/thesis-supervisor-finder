drop table IF EXISTS topic_requirements;
drop table IF EXISTS topic;
drop table IF EXISTS passed_courses;
drop table IF EXISTS interests;
drop table IF EXISTS users;

create TABLE users (
    github_user_id BIGINT PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL
);

create TABLE interests (
    user_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(github_user_id)
);

create TABLE passed_courses (
    user_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(github_user_id)
);

create TABLE topic (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    supervisor_id BIGINT NOT NULL,
    FOREIGN KEY (supervisor_id) REFERENCES users(github_user_id)
);

create TABLE topic_requirements (
    topic_id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    FOREIGN KEY (topic_id) REFERENCES topic(id)
);