package az.company.customer.config;

import az.company.customer.util.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author QasimovEY on 1/14/2021
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class AppFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        setLogId(httpServletRequest);
        httpServletRequest.setAttribute("elapsed-time", System.currentTimeMillis());
        MDC.remove("log_id");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void setLogId(HttpServletRequest httpServletRequest) {
        String logId = LogUtil.logId();
        httpServletRequest.setAttribute("log-id", logId);
        MDC.clear();
        MDC.put("log_id", logId);
    }

}
