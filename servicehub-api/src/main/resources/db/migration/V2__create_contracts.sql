CREATE TABLE contracts (
    id BIGSERIAL PRIMARY KEY,
    provider_id BIGINT NOT NULL REFERENCES providers(id),
    client_id BIGINT NOT NULL REFERENCES clients(id),
    service_category VARCHAR(50) NOT NULL,
    start_date DATE,
    end_date DATE,
    description TEXT
);
