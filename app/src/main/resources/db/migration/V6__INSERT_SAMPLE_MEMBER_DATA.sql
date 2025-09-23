-- Insert sample member data for testing JOOQ custom type mapping
INSERT INTO member (user_id, content) VALUES 
(
    'shawnlee', 
    '{
        "id": "shawnlee",
        "nickName": "shawn",
        "comment": "커피를 사랑하는 랜선집사"
    }'::jsonb
);

-- Additional sample data for better testing
INSERT INTO member (user_id, content) VALUES 
(
    'testuser1', 
    '{
        "id": "testuser1",
        "nickName": "테스터1",
        "comment": "테스트용 사용자입니다"
    }'::jsonb
),
(
    'testuser2', 
    '{
        "id": "testuser2",
        "nickName": "테스터2",
        "comment": "JOOQ 매핑 테스트 중"
    }'::jsonb
);
