package pt.unips.estsetubal.tapoo.adt;

/**
 * ADT para uma árvore genérica ordenada, em que cada nó pode ter zero ou mais filhos.
 *
 * @param <E> tipo dos elementos armazenados
 */
public interface Tree<E> {

    /**
     * Devolve o número de elementos da árvore.
     *
     * @return número de elementos da árvore
     */
    int size();

    /**
     * Indica se a árvore não contém qualquer elemento.
     * @return {@code true} se a árvore estiver vazia; {@code false} caso contrário
     */
    boolean isEmpty();

    /**
     * Devolve a posição da raiz da árvore.
     *
     * @return posição correspondente à raiz
     * @throws EmptyTreeException se a árvore estiver vazia
     */
    Position<E> root() throws EmptyTreeException;

    /**
     * Devolve a posição do pai da posição indicada.
     *
     * @param position posição cujo pai se pretende obter
     * @return posição correspondente ao pai de {@code position}
     * @throws InvalidPositionException se {@code position} não for uma posição válida
     * @throws BoundaryViolationException se {@code position} corresponder à raiz
     */
    Position<E> parent(Position<E> position)
            throws InvalidPositionException, BoundaryViolationException;

    /**
     * Devolve as posições dos filhos da posição indicada, pela sua ordem.
     *
     * @param position posição cujos filhos se pretende obter
     * @return posições dos filhos de {@code position}
     * @throws InvalidPositionException se {@code position} não for uma posição válida
     */
    Iterable<Position<E>> children(Position<E> position)
            throws InvalidPositionException;

    /**
     * Indica se a posição indicada conresponde a un nó interno, isto é, se tem pelo menos um filho.
     *
     * @param position posição a testar
     * @return {@code true} se {@code position} for interna; {@code false} caso contrário
     * @throws InvalidPositionException se {@code position} não for uma posição válida
     */
    boolean isInternal(Position<E> position) throws InvalidPositionException;

    /**
     * Indica se a posição indicada é externa, isto é, se não tem filhos.
     *
     * @param position posição a testar
     * @return {@code true} se {@code position} for externa; {@code false} caso contrário
     * @throws InvalidPositionException se {@code position} não for uma posição válida
     */
    boolean isExternal(Position<E> position) throws InvalidPositionException;

    /**
     * Indica se a posição indicada corresponde à raiz da árvore.
     *
     * @param position posição a testar
     * @return {@code true} se {@code position} for a raiz; {@code false} caso contrário
     * @throws InvalidPositionException se {@code position} não for uma posição válida
     */
    boolean isRoot(Position<E> position) throws InvalidPositionException;

    /**
     * Devolve todas as posições da árvore em pré-ordem.
     *
     * @return posições da árvore em pré-ordem
     */
    Iterable<Position<E>> positions();

    /**
     * Devolve todos os elementos armazenados na árvore em pré-ordem.
     *
     * @return elementos da árvore em pré-ordem
     */
    Iterable<E> elements();

    /**
     * Insere um elemento como último filho de parent. Numa árvore vazia,
     * parent deve ser null e o elemento inserido torna-se a raiz.
     *
     * @param parent posição do pai do novo elemento, ou {@code null} se a árvore estiver vazia
     * @param element elemento a inserir
     * @return posição onde o elemento foi inserido
     * @throws InvalidPositionException se {@code parent} não for uma posição válida
     */
    Position<E> insert(Position<E> parent, E element)
            throws InvalidPositionException;

    /**
     * Insere um elemento na posição order da lista de filhos de parent.
     * Numa árvore vazia, parent deve ser null e order deve ser 0.
     *
     * @param parent posição do pai do novo elemento, ou {@code null} se a árvore estiver vazia
     * @param element elemento a inserir
     * @param order índice onde o novo filho será inserido
     * @return posição onde o elemento foi inserido
     * @throws InvalidPositionException se {@code parent} não for uma posição válida
     * @throws BoundaryViolationException se {@code order} não for uma posição válida na lista de filhos
     */
    Position<E> insert(Position<E> parent, E element, int order)
            throws InvalidPositionException, BoundaryViolationException;

    /**
     * insere o elemento armazenado na posição indicada.
     *
     * @param position posição cujo elemento será substituído
     * @param element novo elemento a armazenar
     * @return elemento anteriormente armazenado em {@code position}
     * @throws InvalidPositionException se {@code position} não for uma posição válida
     */
    E replace(Position<E> position, E element) throws InvalidPositionException;

    /**
     * Remove a subárvore cuja raiz é position e devolve o elemento aí armazenado.
     * Todas as posições dessa subárvore ficam inválidas.
     *
     * @param position raiz da subárvore a remover
     * @return elemento armazenado na posição removida
     * @throws InvalidPositionException se {@code position} não for uma posição válida
     */
    E remove(Position<E> position) throws InvalidPositionException;
}
