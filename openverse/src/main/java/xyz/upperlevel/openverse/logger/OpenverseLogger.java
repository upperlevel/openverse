package xyz.upperlevel.openverse.logger;

import xyz.upperlevel.openverse.OpenverseProxy;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class OpenverseLogger extends Logger {
    private final String loggerName;

    public OpenverseLogger(OpenverseProxy openverse) {
        super(openverse.getClass().getCanonicalName(), null);
        this.loggerName = openverse.getClass().getSimpleName();
        setLevel(Level.ALL);
    }

    @Override
    public void log(LogRecord log) {
        log.setMessage("[" + loggerName + "] " + log.getMessage());
        super.log(log);
    }
}
