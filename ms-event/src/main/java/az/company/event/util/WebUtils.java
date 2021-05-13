package az.company.event.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

/**
 * @Author QasimovEY on 24.02.21
 */

@Slf4j
@Component
public class WebUtils {
    private HttpServletRequest httpServletRequest;

    @Autowired
    public void setRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public String getClientIp() {
        if (httpServletRequest == null) return "";
        return Optional
                .ofNullable(httpServletRequest.getHeader("X-Forwarded-For"))
                .stream()
                .flatMap(ip -> Arrays.stream(ip.split(",")))
                .findFirst()
                .orElse(httpServletRequest.getRemoteAddr());
    }

    public String getLogId() {
        return Optional.ofNullable(httpServletRequest)
                .map(req -> String.valueOf(req.getAttribute("log-id")))
                .orElse(null);
    }

    public String getRequestUri() {
        return Optional.ofNullable(httpServletRequest)
                .map(req -> req.getMethod() + " " + req.getRequestURI())
                .orElse("");
    }

    public Long getElapsedTime() {
        return Optional.ofNullable(httpServletRequest)
                .map(req -> String.valueOf(req.getAttribute("elapsed-time")))
                .filter(StringUtils::isNumeric)
                .map(t -> System.currentTimeMillis() - Long.parseLong(t))
                .orElse(-1L);
    }

}
