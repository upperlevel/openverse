package xyz.upperlevel.openverse.console.log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.LogRecord;

public class LogDispatcher extends Thread {
    private final OpenverseLogger logger;
    private final BlockingQueue<LogRecord> queue = new LinkedBlockingQueue<>();

    protected LogDispatcher(OpenverseLogger logger) {
        super("Logger Thread");
        this.logger = logger;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            LogRecord record;
            try {
                record = queue.take();
            } catch ( InterruptedException ex ) {
                break;
            }

            logger.doLog(record);
        }
        queue.forEach(logger::doLog);
        System.out.println("Stopped dispatcher");
    }

    public void queue(LogRecord record) {
        if (!isInterrupted()) {
            queue.add( record );
        }
    }
}
