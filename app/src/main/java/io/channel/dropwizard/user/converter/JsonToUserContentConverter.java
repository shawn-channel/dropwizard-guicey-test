package io.channel.dropwizard.user.converter;

import org.jooq.Converter;
import org.jooq.JSONB;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.channel.dropwizard.user.model.UserContent;

public class JsonToUserContentConverter implements Converter<JSONB, UserContent> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public UserContent from(JSONB databaseObject) {
        try {
            return objectMapper.readValue(databaseObject.data(), UserContent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to UserContent: " + databaseObject.data(), e);
        }
    }

    @Override
    public JSONB to(UserContent userObject) {
        try {
            return JSONB.jsonbOrNull(objectMapper.writeValueAsString(userObject));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert UserContent to JSONB: " + userObject, e);
        }
    }

    @Override
    public Class<JSONB> fromType() {
        return JSONB.class;
    }

    @Override
    public Class<UserContent> toType() {
        return UserContent.class;
    }

}
