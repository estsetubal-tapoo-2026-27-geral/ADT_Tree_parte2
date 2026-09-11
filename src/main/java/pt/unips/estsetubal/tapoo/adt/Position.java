package pt.unips.estsetubal.tapoo.adt;

/**
 * Referência abstrata a um lugar numa estrutura de dados.
 *
 * @param <E> tipo do elemento armazenado
 */
public interface Position<E> {

    /**
     * @return elemento armazenado nesta posição
     * @throws InvalidPositionException se a posição já não for válida
     */
    E element() throws InvalidPositionException;
}
