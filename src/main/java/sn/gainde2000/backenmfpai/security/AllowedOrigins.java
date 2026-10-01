package sn.gainde2000.backenmfpai.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Component
public class AllowedOrigins {
    private final List<String> values;

    public AllowedOrigins(@Value("${app.security.allowed-origins:http://localhost:4200}") String configured) {
        values = Arrays.stream(configured.split(",")).map(String::trim).filter(s -> !s.isEmpty())
                .map(AllowedOrigins::validate).distinct().toList();
        if (values.isEmpty()) throw new IllegalArgumentException("At least one allowed origin is required");
    }

    private static String validate(String value) {
        URI uri = URI.create(value);
        if (!("https".equals(uri.getScheme()) || "http".equals(uri.getScheme()))
                || uri.getHost() == null || uri.getUserInfo() != null || uri.getQuery() != null
                || uri.getFragment() != null || !(uri.getPath().isEmpty() || "/".equals(uri.getPath()))) {
            throw new IllegalArgumentException("CORS requires an explicit HTTP(S) origin without path or wildcard");
        }
        return value.endsWith("/") ? value.substring(0, value.length() - 1) : value;
    }

    public List<String> values() { return values; }
}
