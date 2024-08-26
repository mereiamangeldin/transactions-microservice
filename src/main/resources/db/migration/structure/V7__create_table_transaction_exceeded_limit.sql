CREATE TABLE transaction_exceeded_limits (
    id bigint generated by default as identity primary key,
    transaction_id BIGINT NOT NULL,
    limit_id BIGINT NOT NULL,
    exceededAmount FLOAT,
    CONSTRAINT fk_transaction_exceeded_limits_transaction_id FOREIGN KEY (transaction_id) REFERENCES transactions(id),
    CONSTRAINT fk_transaction_exceeded_limits_limit_id FOREIGN KEY (limit_id) REFERENCES limits(id)
);

CREATE INDEX idx_transaction_id ON transaction_exceeded_limits(transaction_id);