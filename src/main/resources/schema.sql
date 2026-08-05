CREATE TABLE IF NOT EXISTS application_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    level VARCHAR(16) NOT NULL,
    message VARCHAR(1024) NOT NULL,
    endpoint VARCHAR(256),
    status_code INT,
    created_at DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS deployment_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    version VARCHAR(64) NOT NULL,
    deployment_time DATETIME NOT NULL,
    environment VARCHAR(32) NOT NULL,
    image_tag VARCHAR(128) NOT NULL,
    status VARCHAR(32) NOT NULL,
    git_commit_id VARCHAR(64),
    branch_name VARCHAR(64),
    build_number VARCHAR(64)
);
