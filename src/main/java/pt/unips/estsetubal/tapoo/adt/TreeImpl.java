package pt.unips.estsetubal.tapoo.adt;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação ligada do ADT Tree.
 *
 * @param <E> tipo dos elementos armazenados
 */
public class TreeImpl<E> implements Tree<E> {

    private TreeNode root;

    public TreeImpl() {
        root = null;
    }

    public TreeImpl(E rootElement) {
        root = new TreeNode(rootElement, null);
    }

    @Override
    public int size() {
        int count = 0;
        for (Position<E> ignored : positions()) {
            count++;
        }
        return count;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public Position<E> root() throws EmptyTreeException {
        if (isEmpty()) {
            throw new EmptyTreeException();
        }
        return root;
    }

    @Override
    public Position<E> parent(Position<E> position)
            throws InvalidPositionException, BoundaryViolationException {
        TreeNode node = checkPosition(position);
        if (node == root) {
            throw new BoundaryViolationException("A raiz não tem pai.");
        }
        return node.parent;
    }

    @Override
    public Iterable<Position<E>> children(Position<E> position)
            throws InvalidPositionException {
        TreeNode node = checkPosition(position);
        List<Position<E>> result = new ArrayList<>();
        result.addAll(node.children);
        return result;
    }

    @Override
    public boolean isInternal(Position<E> position) throws InvalidPositionException {
        // TODO A2.2: um nó interno tem, pelo menos, um filho.
        throw new UnsupportedOperationException("Método isInternal por implementar");
    }

    @Override
    public boolean isExternal(Position<E> position) throws InvalidPositionException {
        // TODO A2.2: um nó externo não tem filhos.
        throw new UnsupportedOperationException("Método isExternal por implementar");
    }

    @Override
    public boolean isRoot(Position<E> position) throws InvalidPositionException {
        // TODO A2.2: validar a posição antes de a comparar com a raiz.
        throw new UnsupportedOperationException("Método isRoot por implementar");
    }

    @Override
    public Position<E> insert(Position<E> parent, E element)
            throws InvalidPositionException {
        if (isEmpty()) {
            if (parent != null) {
                throw new InvalidPositionException(
                        "Numa árvore vazia, a posição-pai deve ser null.");
            }
            root = new TreeNode(element, null);
            return root;
        }

        TreeNode parentNode = checkPosition(parent);
        TreeNode newNode = new TreeNode(element, parentNode);
        parentNode.children.add(newNode);
        return newNode;
    }

    @Override
    public Position<E> insert(Position<E> parent, E element, int order)
            throws InvalidPositionException, BoundaryViolationException {
        if (isEmpty()) {
            if (parent != null) {
                throw new InvalidPositionException(
                        "Numa árvore vazia, a posição-pai deve ser null.");
            }
            if (order != 0) {
                throw new BoundaryViolationException(
                        "A raiz apenas pode ser inserida na ordem 0.");
            }
            root = new TreeNode(element, null);
            return root;
        }

        TreeNode parentNode = checkPosition(parent);
        if (order < 0 || order > parentNode.children.size()) {
            throw new BoundaryViolationException("Índice de inserção inválido.");
        }

        TreeNode newNode = new TreeNode(element, parentNode);
        parentNode.children.add(order, newNode);
        return newNode;
    }

    @Override
    public E replace(Position<E> position, E element)
            throws InvalidPositionException {
        TreeNode node = checkPosition(position);
        E previousElement = node.element;
        node.element = element;
        return previousElement;
    }

    @Override
    public E remove(Position<E> position) throws InvalidPositionException {
        // TODO A2.2: validar a posição e rejeitar um nó com filhos.
        // Desligar a folha do pai (ou esvaziar a raiz), invalidá-la e
        // devolver o elemento anteriormente armazenado.
        throw new UnsupportedOperationException("Método remove por implementar");
    }

    @Override
    public Iterable<Position<E>> positions() {
        List<Position<E>> result = new ArrayList<>();
        if (root == null) return result;
        java.util.Deque<TreeNode> pending = new java.util.ArrayDeque<>();
        pending.push(root);
        while (!pending.isEmpty()) {
            TreeNode node = pending.pop();
            result.add(node);
            for (int i = node.children.size() - 1; i >= 0; i--) {
                pending.push(node.children.get(i));
            }
        }
        return result;
    }

    @Override
    public Iterable<E> elements() {
        List<E> result = new ArrayList<>();
        if (!isEmpty()) {
            collectElementsPreOrder(root, result);
        }
        return result;
    }

    private void collectElementsPreOrder(TreeNode node, List<E> result) {
        result.add(node.element);
        for (TreeNode child : node.children) {
            collectElementsPreOrder(child, result);
        }
    }

    /**
     * Valida e converte uma posição recebida pelas operações da árvore.
     *
     * TODO A2.2: completar progressivamente a partir dos testes de validação.
     */
    private TreeNode checkPosition(Position<E> position)
            throws InvalidPositionException {
        return (TreeNode) position;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        appendTree(root, 0, result);
        return result.toString();
    }

    private void appendTree(TreeNode node, int level, StringBuilder result) {
        if (level > 0) {
            result.append("  ".repeat(level - 1)).append("- ");
        }
        result.append(node.element).append(System.lineSeparator());
        for (TreeNode child : node.children) {
            appendTree(child, level + 1, result);
        }
    }

    /** Nó privado da implementação ligada. */
    private class TreeNode implements Position<E> {
        // Referência para o elemento armazenado nesta posição.
        private E element;

        // Referência para o nó pai; é null na raiz.
        private TreeNode parent;

        // Referências para os nós filhos, pela ordem em que aparecem na árvore.
        private final List<TreeNode> children;

        // Referência para a instância da árvore que criou este nó.
        // Permite rejeitar a sua utilização noutra árvore.
        private final TreeImpl<E> owner;

        // Indica se o nó ainda é uma posição válida da árvore.
        // Passa a false quando o nó é removido.
        private boolean valid;


        TreeNode(E element, TreeNode parent) {
            this.element = element;
            this.parent = parent;
            this.children = new ArrayList<>();
            this.owner = TreeImpl.this;
            this.valid = true;
        }

        @Override
        public E element() throws InvalidPositionException {
            if (!valid) {
                throw new InvalidPositionException("A posição já foi removida.");
            }
            return element;
        }
    }
}
