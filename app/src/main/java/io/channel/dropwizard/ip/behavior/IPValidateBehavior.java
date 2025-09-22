package io.channel.dropwizard.ip.behavior;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.executable.ValidateOnExecution;

import org.apache.commons.net.util.SubnetUtils;
import io.channel.dropwizard.ip.util.CIDRMatcher;
import io.channel.dropwizard.jooq.tables.records.AllowedIpRecord;
import org.jooq.DSLContext;

import static io.channel.dropwizard.jooq.Tables.ALLOWED_IP;

public class IPValidateBehavior {
    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";
    private static final String LOCALHOST = "127.0.0.1";
    
    // Database-driven constants
    private static final String TYPE_EXACT = "EXACT";
    private static final String TYPE_CIDR = "CIDR";
    
    private final String officeCidr;
    private final DSLContext dsl;

    @Inject 
    public IPValidateBehavior(@Named("OFFICE_CIDR") String officeCidr, DSLContext dsl) {
        this.officeCidr = officeCidr;
        this.dsl = dsl;
    }

    @ValidateOnExecution // 주석처리다가 풀어가며 변화를 확인하세요!
    public String validate(@NotNull Boolean isOffice, String ipV4Address) {
        // 1. Check office CIDR (existing logic - for backward compatibility)
        if (isOffice != null && isOffice && isOfficeIp(ipV4Address)) {
            return SUCCESS;
        }

        // 2. Check localhost (existing logic)
        if (LOCALHOST.equals(ipV4Address) && isOfficeIp(ipV4Address)) {
            return SUCCESS;
        }

        // 3. Check database-stored allowed IPs/CIDRs
        if (isAllowedByDatabase(ipV4Address, isOffice)) {
            return SUCCESS;
        }

        return FAIL;
    }

    private boolean isOfficeIp(String ipV4Address) {
        SubnetUtils subnet = new SubnetUtils(officeCidr);
        return subnet.getInfo().isInRange(ipV4Address);
    }
    
    private boolean isAllowedByDatabase(String ipV4Address, Boolean isOffice) {
        try {
            // Query allowed IPs from database
            // If isOffice is specified, filter by is_office flag; otherwise check all
            List<AllowedIpRecord> allowedIps;
            
            if (isOffice != null) {
                allowedIps = dsl.selectFrom(ALLOWED_IP)
                    .where(ALLOWED_IP.IS_OFFICE.eq(isOffice))
                    .fetch();
            } else {
                // Check both office and non-office IPs
                allowedIps = dsl.selectFrom(ALLOWED_IP)
                    .fetch();
            }
            
            // Check each allowed IP/CIDR against the input
            for (AllowedIpRecord record : allowedIps) {
                if (matchesAllowedIP(ipV4Address, record)) {
                    return true;
                }
            }
            
            return false;
            
        } catch (Exception e) {
            // Log the database error (you might want to add a logger)
            // In case of database failure, fall back to FAIL for security
            System.err.println("Database error during IP validation: " + e.getMessage());
            return false;
        }
    }
    
    private boolean matchesAllowedIP(String ipV4Address, AllowedIpRecord record) {
        String allowedValue = record.getValue();
        String type = record.getType();
        
        if (TYPE_EXACT.equals(type)) {
            // Exact IP match
            return allowedValue.equals(ipV4Address);
        } else if (TYPE_CIDR.equals(type)) {
            // CIDR range match
            return CIDRMatcher.isIpInCidr(ipV4Address, allowedValue);
        } else {
            // Auto-detect: if value contains '/', treat as CIDR; otherwise exact match
            if (allowedValue.contains("/")) {
                return CIDRMatcher.isIpInCidr(ipV4Address, allowedValue);
            } else {
                return allowedValue.equals(ipV4Address);
            }
        }
    }
}