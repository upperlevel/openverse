package xyz.upperlevel.openverse.console.log;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

@AllArgsConstructor
public class ColoredWriter extends Handler {
    private PrintStream out;

    public void print(String s) throws IOException {
        out.write(s.getBytes(StandardCharsets.UTF_8));
        out.flush();
    }

    @Override
    public void publish(LogRecord log) {
        if (isLoggable(log)) {
            try {
                print(getFormatter().format(log));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
    }
}
