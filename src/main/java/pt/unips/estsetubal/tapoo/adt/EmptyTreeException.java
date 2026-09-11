package pt.unips.estsetubal.tapoo.adt;

public class EmptyTreeException extends RuntimeException {
    public EmptyTreeException() {
        super("A árvore está vazia.");
    }
}
