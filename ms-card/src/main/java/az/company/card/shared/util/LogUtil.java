package az.company.card.shared.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author QasimovEY on 24.02.21
 */
public final class LogUtil {

    private LogUtil() {
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    private static final AtomicInteger counter = new AtomicInteger(100_000);

    public static String logId() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String logId = localDateTime.format(formatter) + "C" + counter.getAndIncrement();
        return logId;
    }

    public static String getParams(final String[] paramNames, final Object[] paramValues) {
        return IntStream.range(0, paramNames.length)
                .mapToObj(i -> paramNames[i] + "=" + paramValues[i])
                .collect(Collectors.joining(", ", "[", "]"));
    }

    public static Map<String, Object> getParamsAsMap(final String[] paramNames, final Object[] paramValues) {
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++)
            params.put(paramNames[i], paramValues[i]);
        return params;
    }

}
