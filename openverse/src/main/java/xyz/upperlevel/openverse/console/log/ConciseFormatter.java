package xyz.upperlevel.openverse.console.log;

import xyz.upperlevel.openverse.console.ChatColor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ConciseFormatter extends Formatter {
    private final DateFormat date = new SimpleDateFormat("HH:mm:ss");

    @Override
    public String format(LogRecord record) {
        String levelName = record.getLevel().getName();
        String formattedMessage = formatMessage(record);

        StringBuilder formatted = new StringBuilder(8 + 1 + (2 + levelName.length()) + 1 + formattedMessage.length() + ChatColor.RESET.length() + 1);

        formatted.append(date.format(record.getMillis()));
        formatted.append(" [");
        formatted.append(levelName);
        formatted.append("] ");
        formatted.append(formattedMessage);
        formatted.append(ChatColor.RESET);
        formatted.append('\n');
        if (record.getThrown() != null) {
            StringWriter writer = new StringWriter();
            record.getThrown().printStackTrace(new PrintWriter(writer));
            formatted.append(writer);
        }

        return formatted.toString();
    }
}
