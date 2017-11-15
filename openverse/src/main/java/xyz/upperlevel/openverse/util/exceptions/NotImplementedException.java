package xyz.upperlevel.openverse.util.exceptions;

public class NotImplementedException extends UnsupportedOperationException {
    public NotImplementedException() {
        super("Not implemented yet");
    }

    public NotImplementedException(String reason) {
        super(reason);
    }
}
