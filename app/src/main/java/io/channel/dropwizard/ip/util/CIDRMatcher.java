package io.channel.dropwizard.ip.util;

import java.util.regex.Pattern;

public class CIDRMatcher {
    // IPv4 주소 검증용 패턴
    private static final Pattern IPV4_PATTERN = Pattern.compile(
        "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
    );
    
    // CIDR 형식 검증용 패턴
    private static final Pattern CIDR_PATTERN = Pattern.compile(
        "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)/(?:[0-9]|[1-2][0-9]|3[0-2])$"
    );

    /**
     * CIDR 대역과 IP 주소가 매칭되는지 확인
     * @param ipAddress IPv4 주소 (예: "192.168.8.100")
     * @param cidr CIDR 형식 대역 (예: "192.168.8.0/21")
     * @return 매칭 여부
     */
    public static boolean isIpInCidr(String ipAddress, String cidr) {
        // 입력값 검증
        if (ipAddress == null || cidr == null) {
            return false;
        }
        
        if (!IPV4_PATTERN.matcher(ipAddress).matches()) {
            throw new IllegalArgumentException("Invalid IPv4 address: " + ipAddress);
        }
        
        if (!CIDR_PATTERN.matcher(cidr).matches()) {
            throw new IllegalArgumentException("Invalid CIDR format: " + cidr);
        }
        
        try {
            // CIDR 파싱
            String[] cidrParts = cidr.split("/");
            String networkAddress = cidrParts[0];
            int prefixLength = Integer.parseInt(cidrParts[1]);
            
            // IP 주소들을 바이트 배열로 변환
            byte[] targetBytes = ipToBytes(ipAddress);
            byte[] networkBytes = ipToBytes(networkAddress);
            
            // CIDR 매칭 수행
            return isInNetwork(targetBytes, networkBytes, prefixLength);
            
        } catch (Exception e) {
            throw new IllegalArgumentException("Error processing CIDR matching", e);
        }
    }
    
    /**
     * IP 주소 문자열을 바이트 배열로 변환
     */
    private static byte[] ipToBytes(String ip) {
        String[] octets = ip.split("\\.");
        byte[] bytes = new byte[4];
        
        for (int i = 0; i < 4; i++) {
            int octet = Integer.parseInt(octets[i]);
            bytes[i] = (byte) octet;
        }
        
        return bytes;
    }
    
    /**
     * 실제 네트워크 매칭 로직
     */
    private static boolean isInNetwork(byte[] targetBytes, byte[] networkBytes, int prefixLength) {
        // 전체 바이트 비교할 개수
        int bytesToCheck = prefixLength / 8;
        // 마지막 바이트에서 비교할 비트 수
        int bitsToCheck = prefixLength % 8;
        
        // 전체 바이트들 비교
        for (int i = 0; i < bytesToCheck; i++) {
            if (targetBytes[i] != networkBytes[i]) {
                return false;
            }
        }
        
        // 마지막 바이트의 일부 비트만 비교 (필요한 경우)
        if (bitsToCheck > 0 && bytesToCheck < 4) {
            // 마스크 생성: 상위 bitsToCheck 비트만 1로 설정
            int mask = 0xFF << (8 - bitsToCheck);
            
            int targetPart = (targetBytes[bytesToCheck] & 0xFF) & mask;
            int networkPart = (networkBytes[bytesToCheck] & 0xFF) & mask;
            
            return targetPart == networkPart;
        }
        
        return true;
    }
}