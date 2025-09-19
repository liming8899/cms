package cloud.onion.cms.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Test {

    public static void main(String[] args) {
        String[] ips = {"223.71.133.42","112.13.112.73","172.31.8.1","10.200.255.253","10.200.255.252","222.81.75.130","107.0.5304.141","183.26.115.152","123.168.73.10","101.67.49.5"};
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        LocalDateTime startDateTime = LocalDateTime.of(2022, 11, 10, 8, 12, 34);
        LocalDateTime endDateTime = LocalDateTime.of(2023, 5, 12, 21, 0, 0);

        LocalDateTime[] timePoints = extractTimePoints(startDateTime, endDateTime, 1124);

        // 打印提取的时间点
        for (LocalDateTime timePoint : timePoints) {
            StringBuffer buffer = new StringBuffer(ips[(int)(Math.random()*10)]);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy:HH:mm:ss");
            String format = timePoint.format(formatter);

            buffer.append(" - - [").append(format, 0, 3).append(months[Integer.parseInt(format.substring(3, 5)) - 1]).append(format.substring(5)).append(" +0800] \"HEAD / HTTP/1.0\" 301 0 \"-\" \"-\"");
            System.out.println(buffer.toString());
        }
    }

    public static LocalDateTime[] extractTimePoints(LocalDateTime startDateTime, LocalDateTime endDateTime, int count) {
        LocalDateTime[] timePoints = new LocalDateTime[count];
        long duration = ChronoUnit.SECONDS.between(startDateTime, endDateTime);
        long interval = duration / (count + 1);

        for (int i = 0; i < count; i++) {
            timePoints[i] = startDateTime.plusSeconds(interval * (i + 1));
        }

        return timePoints;
    }
}
