package utils;
import java.util.Random;

public class RandomCodeGenerator {

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
        Random random = new Random();
        for (int count = 0; count < 8; count++) {
            code.append(random.nextBoolean() ? (char) ('A' + random.nextInt(26)) : random.nextInt(10));
        }
        return code.toString();
    }

    private static String getString(StringBuilder code) {
        Random random = new Random();
        for (int count = 0; count < 8; count++) {
            if (count == 4) {code.append("-");}
            code.append(random.nextBoolean() ? (char) ('A' + random.nextInt(26)) : random.nextInt(10));
        }
        return code.toString();
    }

    private static String reuseableIdGenerator(String codePrefix){
        StringBuilder code = new StringBuilder(codePrefix);
        return getString(code);
    }
}
