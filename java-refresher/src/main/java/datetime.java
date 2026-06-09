import java.time.LocalDateTime;

public class datetime {
    public static void main(String[] args) {
        var day = timeNow().getDayOfMonth();
        var month = timeNow().getMonth();
        var year = timeNow().getYear();

        System.out.println(day);
        System.out.println(month);
        System.out.println(year);
    }

    public static LocalDateTime timeNow() {
        return LocalDateTime.now();
    }
}
