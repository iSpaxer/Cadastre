package TgBot.testJava;

import java.util.HashMap;
import java.util.Map;

public class TestJava {
    public static void main(String[] args) {
        Map<String, Integer> stringStringMap = new HashMap<>();
        stringStringMap.put("1", 1);
//        System.out.println(stringStringMap.);

        System.out.println("entrySet " + stringStringMap.entrySet());
    }
}
