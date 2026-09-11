package pt.unips.estsetubal.tapoo.adt;

/**
 * ADT para uma árvore genérica ordenada, em que cada nó pode ter zero ou mais filhos.
 *
 * @param <E> tipo dos elementos armazenados
 */
public interface Tree<E> {

    int size();

    boolean isEmpty();

    Position<E> root() throws EmptyTreeException;

    Position<E> parent(Position<E> position)
            throws InvalidPositionException, BoundaryViolationException;

    Iterable<Position<E>> children(Position<E> position)
            throws InvalidPositionException;

    boolean isInternal(Position<E> position) throws InvalidPositionException;

    boolean isExternal(Position<E> position) throws InvalidPositionException;

    boolean isRoot(Position<E> position) throws InvalidPositionException;

    Iterable<Position<E>> positions();

    /** Devolve os elementos em pré-ordem. */
    Iterable<E> elements();

    /**
     * Insere um elemento como último filho de parent. Numa árvore vazia,
     * parent deve ser null e o elemento inserido torna-se a raiz.
     */
    Position<E> insert(Position<E> parent, E element)
            throws InvalidPositionException;

    /**
     * Insere um elemento na posição order da lista de filhos de parent.
     * Numa árvore vazia, parent deve ser null e order deve ser 0.
     */
    Position<E> insert(Position<E> parent, E element, int order)
            throws InvalidPositionException, BoundaryViolationException;

    E replace(Position<E> position, E element) throws InvalidPositionException;

    /**
     * Remove a subárvore cuja raiz é position e devolve o elemento aí armazenado.
     * Todas as posições dessa subárvore ficam inválidas.
     */
    E remove(Position<E> position) throws InvalidPositionException;
}
