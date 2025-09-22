package io.channel.dropwizard.ip.behavior;

import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.executable.ValidateOnExecution;

import org.apache.commons.net.util.SubnetUtils;

public class IPValidateBehavior {
    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";
    private static final String LOCALHOST = "127.0.0.1";
    private static final Set<String> ALLOWED_EXTERNAL_IPS = Set.of("30.10.12.13", "27.1.3.124");
    private final String officeCidr;

    @Inject 
    public IPValidateBehavior(@Named("OFFICE_CIDR") String officeCidr) {
        this.officeCidr = officeCidr;
    }

    @ValidateOnExecution // 주석처리다가 풀어가며 변화를 확인하세요!
    public String validate(@NotNull Boolean isOffice, String ipV4Address) {
        if(isOffice != null && isOffice && isOfficeIp(ipV4Address)) {
            return SUCCESS;
        }

        if(LOCALHOST.equals(ipV4Address) && isOfficeIp(ipV4Address)) {
            return SUCCESS;
        }

        if(ALLOWED_EXTERNAL_IPS.contains(ipV4Address)) {
            return SUCCESS;
        }

        return FAIL;
    }

    private boolean isOfficeIp(String ipV4Address) {
        SubnetUtils subnet = new SubnetUtils(officeCidr);
        return subnet.getInfo().isInRange(ipV4Address);
    }
}
