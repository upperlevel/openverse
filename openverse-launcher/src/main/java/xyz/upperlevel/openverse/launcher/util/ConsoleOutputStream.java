package xyz.upperlevel.openverse.launcher.util;

import jline.console.ConsoleReader;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Adapter for a Writer to behave like an OutputStream.
 *
 * Bytes are converted to chars using the platform default encoding.
 * If this encoding is not a single-byte encoding, some data may be lost.
 */
@RequiredArgsConstructor
public class ConsoleOutputStream extends OutputStream {
    private final ConsoleReader console;

    public void write(int b) throws IOException {
        //It's tempting to use console.write((char) b), but that may get the encoding wrong
        //This is inefficient, but it works
        write(new byte[] {(byte) b}, 0, 1);
    }

    public void write(byte b[], int off, int len) throws IOException {
        console.print(ConsoleReader.RESET_LINE + "");
        console.flush();
        console.print(new String(b, off, len));
        console.drawLine();
        console.flush();
    }

    public void flush() throws IOException {
        //No point, already flushing in every write
        //console.flush();
    }

    public void close() throws IOException {
        console.close();
    }
}