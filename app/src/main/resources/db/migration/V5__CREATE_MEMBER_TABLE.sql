-- Create member table for JOOQ custom type mapping example
DROP TABLE IF EXISTS member;

CREATE TABLE member (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(50) UNIQUE NOT NULL,
    content JSONB NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Add index for user_id for better query performance
CREATE UNIQUE INDEX idx_member_user_id ON member(user_id);
