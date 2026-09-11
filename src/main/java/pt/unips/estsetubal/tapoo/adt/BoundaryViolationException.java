package pt.unips.estsetubal.tapoo.adt;

public class BoundaryViolationException extends RuntimeException {
    public BoundaryViolationException(String message) {
        super(message);
    }
}
