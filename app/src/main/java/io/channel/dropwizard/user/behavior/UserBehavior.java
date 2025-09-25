package io.channel.dropwizard.user.behavior;

import io.channel.dropwizard.user.dao.MemberDao;
import io.channel.dropwizard.user.model.UserContent;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Optional;

/**
 * Business logic layer for user operations
 * Demonstrates JOOQ custom type mapping usage in service layer
 */
@Singleton
public class UserBehavior {
    
    private final MemberDao memberDao;
    
    @Inject
    public UserBehavior(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
    
    /**
     * Get user content by user ID
     * This method demonstrates how JOOQ custom type mapping works seamlessly
     * - Database JSONB is automatically converted to UserContent object
     * - No manual JSON parsing required
     */
    public Optional<UserContent> getUserContent(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return Optional.empty();
        }
        
        return memberDao.findContentByUserId(userId.trim());
    }
}
