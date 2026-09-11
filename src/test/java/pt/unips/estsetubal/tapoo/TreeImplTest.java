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

import java.util.ArrayList;
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

    @Test
    void nullPositionIsRejected() {
        assertThrows(InvalidPositionException.class,
                () -> tree.children(null));
    }

    @Test
    void positionFromAnotherTreeIsRejected() {
        Tree<FileSystemItem> otherTree = new TreeImpl<>();
        Position<FileSystemItem> otherRoot = otherTree.insert(null, folder("Outra"));

        assertThrows(InvalidPositionException.class,
                () -> tree.children(otherRoot));
    }

    @Test
    void elementsFollowPreOrder() {
        List<String> names = new ArrayList<>();
        for (FileSystemItem item : tree.elements()) {
            names.add(item.getName());
        }

        assertEquals(List.of("Computador", "Documentos", "aulas.pdf",
                "notas.txt", "Imagens", "ferias.jpg"), names);
    }

    // Retire @Disabled à medida que implementar cada operação.

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
    void positionsFollowPreOrder() {
        List<Position<FileSystemItem>> positions = new ArrayList<>();
        tree.positions().forEach(positions::add);

        assertEquals(List.of(computer, documents, classesPdf, notesTxt,
                images, holidaysJpg), positions);
    }

    @Disabled("Completar depois de implementar remove")
    @Test
    void removingFolderRemovesItsSubtreeAndInvalidatesPositions() {
        assertEquals("Documentos", tree.remove(documents).getName());
        assertThrows(InvalidPositionException.class, documents::element);
        assertThrows(InvalidPositionException.class, classesPdf::element);
        assertThrows(InvalidPositionException.class, notesTxt::element);
        assertEquals(3, tree.size());
    }

    // TODO A2.2: acrescente testes para inserção ordenada, replace,
    // remoção da raiz, posição removida e índices inválidos.
}
