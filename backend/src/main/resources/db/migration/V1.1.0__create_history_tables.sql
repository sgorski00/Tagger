CREATE TABLE IF NOT EXISTS item_descriptions (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    created_by BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE item_descriptions ADD CONSTRAINT fk_created_by FOREIGN KEY (created_by) REFERENCES users(id);

CREATE TABLE IF NOT EXISTS tags (
    id BIGSERIAL PRIMARY KEY,
    item_description_id BIGINT NOT NULL,
    name VARCHAR(255)
);

ALTER TABLE tags ADD CONSTRAINT fk_item_description_id FOREIGN KEY (item_description_id) REFERENCES item_descriptions(id);