package app.sp.metadata.converter.config;

import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {

    private static final Logger LOG = LoggerFactory.getLogger(Config.class);

    private static final Properties PROPS = new Properties();

    private String password;

    private String spMetadataUrl;

    private String username;

    public Config() {
        init();
    }

    private void init() {
        try {
            if (PROPS.isEmpty()) {
                PROPS.load(Config.class.getResourceAsStream("/auth.properties"));
            }

            password = PROPS.getProperty("password");
            username = PROPS.getProperty("username");
            spMetadataUrl = PROPS.getProperty("spMetadataUrl");
        } catch (Exception ex) {
            LOG.error("While accessing auth.properties file info", ex);
            throw new RuntimeException("While accessing auth.properties file info " + ex.getLocalizedMessage());
        }
    }

    public String getPassword() {
        return password;
    }

    public String getSpMetadataUrl() {
        return spMetadataUrl;
    }

    public String getUsername() {
        return username;
    }

}
