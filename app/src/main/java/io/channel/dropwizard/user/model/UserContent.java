package io.channel.dropwizard.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * UserContent model for JOOQ custom type mapping example
 * Represents the JSON content stored in member.content column
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserContent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String nickName;
    private String comment;
}
