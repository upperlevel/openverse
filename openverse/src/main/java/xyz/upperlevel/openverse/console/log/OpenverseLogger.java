package xyz.upperlevel.openverse.console.log;

import lombok.Getter;
import org.fusesource.jansi.AnsiConsole;
import xyz.upperlevel.openverse.OpenverseProxy;
import xyz.upperlevel.openverse.console.ChatColor;
import xyz.upperlevel.openverse.event.ShutdownEvent;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

@Getter
public class OpenverseLogger extends Logger {
    private final Formatter formatter = new CoinciseFormatter();
    private final LogDispatcher dispatcher = new LogDispatcher(this);
    private final String loggerName;

    public OpenverseLogger(OpenverseProxy openverse, String name) {
        super(openverse.getClass().getCanonicalName(), null);
        setLevel(Level.ALL);
        AnsiConsole.systemInstall();

        ColoredWriter handler = new ColoredWriter(System.out);
        handler.setLevel(getLevel());
        handler.setFormatter(formatter);
        addHandler(handler);

        this.loggerName = name;

        openverse.getEventManager().register(ShutdownEvent.class, this::onShutdown);

        dispatcher.start();
    }

    public void setAsDefaultOut() {
        System.setErr(new PrintStream(new LoggingOutputStream(this, Level.SEVERE), true));
        System.setOut(new PrintStream(new LoggingOutputStream(this, Level.INFO), true));
    }

    @Override
    public void log(LogRecord log) {
        log.setMessage(ChatColor.ofLevel(log.getLevel()) + "[" + loggerName + "] " + log.getMessage());
        dispatcher.queue(log);
    }

    public void doLog(LogRecord record) {
        super.log(record);
    }

    public void stop() {
        dispatcher.interrupt();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        AnsiConsole.systemUninstall();
        System.out.println("STOPPING LOGGER " + loggerName);
    }

    public void onShutdown(ShutdownEvent e) {
        stop();
    }
}
