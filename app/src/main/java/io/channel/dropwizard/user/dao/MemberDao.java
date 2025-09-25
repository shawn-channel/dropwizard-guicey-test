package io.channel.dropwizard.user.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.channel.dropwizard.jooq.tables.Member;
import io.channel.dropwizard.user.model.UserContent;
import org.jooq.DSLContext;
import org.jooq.JSONB;
import org.postgresql.util.PGobject;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Optional;

import static io.channel.dropwizard.jooq.tables.Member.MEMBER;

/**
 * Repository for member table operations using JOOQ with manual JSON conversion
 * Example demonstrating PostgreSQL JSONB to UserContent mapping
 */
@Singleton
public class MemberDao {
    
    private final DSLContext dsl;
    
    @Inject
    public MemberDao(DSLContext dsl) {
        this.dsl = dsl;
    }
    
    /**
     * Find user content by user ID
     * Demonstrates manual JSON conversion from PostgreSQL JSONB to UserContent
     */
    public Optional<UserContent> findContentByUserId(String userId) {
        return dsl.select(MEMBER.CONTENT)
                .from(MEMBER)
                .where(MEMBER.USER_ID.eq(userId))
                .fetchOptional()
                .map(o -> o.value1());
    }
}
