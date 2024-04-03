package Boot.cadastreCompany.utils;

import java.util.Optional;

public class TestJava {
    public static void main(String[] args) {
        String b = null;
        var a = Optional.ofNullable(b);
        System.out.println(a.isEmpty());
    }
}
