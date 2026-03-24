package africa.semicolon.utils;
import java.security.SecureRandom;

public class RandomCodeGenerator {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String gateIdGenerator(){
        return reuseableIdGenerator("GP-");
    }

    public static String residentIdGenerator(){
        return reuseableIdGenerator("RES-");
    }

    public static String visitorIdGenerator(){
        return reuseableIdGenerator("VIS-");
    }

    public static String getOtp(){
        StringBuilder code = new StringBuilder();
        for (int count = 0; count < 8; count++) {
            code.append(SECURE_RANDOM.nextBoolean() ? (char) ('A' + SECURE_RANDOM.nextInt(26)) : (char) ('0' + SECURE_RANDOM.nextInt(10)));
        }
        return code.toString();
    }

    private static String getString(StringBuilder code) {
        for (int count = 0; count < 8; count++) {
            if (count == 4) {code.append("-");}
            code.append(SECURE_RANDOM.nextBoolean() ? (char) ('A' + SECURE_RANDOM.nextInt(26)) : (char) ('0' + SECURE_RANDOM.nextInt(10)));
        }
        return code.toString();
    }

    private static String reuseableIdGenerator(String codePrefix){
        StringBuilder code = new StringBuilder(codePrefix);
        return getString(code);
    }
}
