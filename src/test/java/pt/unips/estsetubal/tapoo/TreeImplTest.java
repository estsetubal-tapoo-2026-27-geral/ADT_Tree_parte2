package pt.unips.estsetubal.tapoo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pt.unips.estsetubal.tapoo.adt.BoundaryViolationException;
import pt.unips.estsetubal.tapoo.adt.InvalidPositionException;
import pt.unips.estsetubal.tapoo.adt.Position;
import pt.unips.estsetubal.tapoo.adt.Tree;
import pt.unips.estsetubal.tapoo.adt.TreeImpl;
import pt.unips.estsetubal.tapoo.model.FileSystemItem;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pt.unips.estsetubal.tapoo.model.FileSystemItem.file;
import static pt.unips.estsetubal.tapoo.model.FileSystemItem.folder;

class TreeImplTest {

    private Tree<FileSystemItem> tree;
    private Position<FileSystemItem> computer;
    private Position<FileSystemItem> documents;
    private Position<FileSystemItem> images;
    private Position<FileSystemItem> classesPdf;
    private Position<FileSystemItem> notesTxt;
    private Position<FileSystemItem> holidaysJpg;

    @BeforeEach
    void setUp() {
        tree = new TreeImpl<>();
        computer = tree.insert(null, folder("Computador"));
        documents = tree.insert(computer, folder("Documentos"));
        images = tree.insert(computer, folder("Imagens"));
        classesPdf = tree.insert(documents, file("aulas.pdf"));
        notesTxt = tree.insert(documents, file("notas.txt"));
        holidaysJpg = tree.insert(images, file("ferias.jpg"));
    }

    @Test
    void rootContainsComputer() {
        assertSame(computer, tree.root());
        assertEquals("Computador", tree.root().element().getName());
    }

    @Test
    void parentOfFileIsItsFolder() {
        assertSame(documents, tree.parent(classesPdf));
    }

    @Test
    void parentOfRootThrowsException() {
        assertThrows(BoundaryViolationException.class,
                () -> tree.parent(computer));
    }

    @Disabled("Etapa 1 de checkPosition: retirar para testar uma posição null")
    @Test
    void nullPositionIsRejected() {
        assertThrows(InvalidPositionException.class,
                () -> tree.children(null));
    }

    @Disabled("Etapa 2 de checkPosition: retirar depois de concluir a etapa 1")
    @Test
    void positionFromAnotherImplementationIsRejected() {
        Position<FileSystemItem> externalPosition =
                () -> folder("Externa");

        assertThrows(InvalidPositionException.class,
                () -> tree.children(externalPosition));
    }

    @Disabled("Etapa 3 de checkPosition: retirar depois de concluir a etapa 2")
    @Test
    void positionFromAnotherTreeIsRejected() {
        Tree<FileSystemItem> otherTree = new TreeImpl<>();
        Position<FileSystemItem> otherRoot = otherTree.insert(null, folder("Outra"));

        assertThrows(InvalidPositionException.class,
                () -> tree.children(otherRoot));
    }


    @Disabled("Completar depois de implementar size")
    @Test
    void sizeMatchesNumberOfNodes() {
        assertEquals(6, tree.size());
    }

    @Disabled("Completar depois de implementar isRoot")
    @Test
    void computerIsRoot() {
        assertTrue(tree.isRoot(computer));
        assertFalse(tree.isRoot(documents));
    }

    @Disabled("Completar depois de implementar isInternal e isExternal")
    @Test
    void foldersAndFilesAreClassified() {
        assertTrue(tree.isInternal(documents));
        assertTrue(tree.isExternal(notesTxt));
        assertTrue(tree.isExternal(holidaysJpg));
    }

    @Disabled("Completar depois de implementar positions")
    @Test
    void positionsAreReturnedInPreOrder() {
        assertIterableEquals(
                List.of(computer, documents, classesPdf, notesTxt, images, holidaysJpg),
                tree.positions());
    }



    @Disabled("Completar depois de implementar remove")
    @Test
    void removingLeafInvalidatesOnlyThatPosition() {
        assertEquals("aulas.pdf", tree.remove(classesPdf).getName());
        assertThrows(InvalidPositionException.class, classesPdf::element);
        assertEquals(5, tree.size());
        assertIterableEquals(List.of(notesTxt), tree.children(documents));
        assertSame(documents, tree.parent(notesTxt));
    }

    @Disabled("Completar depois de implementar remove")
    @Test
    void removingInternalNodeThrowsAndPreservesTree() {
        assertThrows(IllegalStateException.class, () -> tree.remove(documents));
        assertEquals(6, tree.size());
        assertIterableEquals(List.of(classesPdf, notesTxt), tree.children(documents));
        assertSame(computer, tree.root());
        assertSame(documents, tree.parent(classesPdf));
    }

    @Disabled("Completar depois de implementar remove")
    @Test
    void removingRootWithChildrenThrowsAndPreservesTree() {
        assertThrows(IllegalStateException.class, () -> tree.remove(computer));
        assertSame(computer, tree.root());
        assertEquals(6, tree.size());
    }

    // TODO A2.2: complete os cinco testes seguintes e retire @Disabled.

    @Disabled("TODO A2.2: completar o teste de inserção ordenada")
    @Test
    void insertWithOrderAddsChildAtSpecifiedPosition() {
        // TODO A2.2: preparar os dados, executar a inserção e verificar
        // a posição do novo filho.
    }

    @Disabled("TODO A2.2: completar o teste de replace")
    @Test
    void replaceChangesElementAndReturnsPreviousElement() {
        // TODO A2.2: substituir um elemento e verificar o valor devolvido
        // e o novo elemento armazenado na posição.
    }

    @Disabled("TODO A2.2: completar o teste de remoção da raiz")
    @Test
    void removingOnlyRootEmptiesTreeAndInvalidatesPosition() {
        // TODO A2.2: criar uma nova árvore só com a raiz, removê-la,
        // verificar isEmpty(), size(), root() e a invalidação da posição.
    }

    @Disabled("TODO A2.2: completar o teste de utilização de posição removida")
    @Test
    void operationWithRemovedPositionThrowsInvalidPositionException() {
        // TODO A2.2: remover uma folha e tentar usar a sua posição numa
        // operação da árvore; verificar InvalidPositionException.
    }

    @Disabled("TODO A2.2: completar o teste de índice inválido")
    @Test
    void insertWithInvalidOrderThrowsBoundaryViolationException() {
        // TODO A2.2: testar um índice que não seja válido para a lista de
        // filhos da posição-pai.
    }
}
