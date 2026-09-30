package pt.unips.estsetubal.tapoo;

import pt.unips.estsetubal.tapoo.adt.Position;
import pt.unips.estsetubal.tapoo.adt.Tree;
import pt.unips.estsetubal.tapoo.adt.TreeImpl;
import pt.unips.estsetubal.tapoo.model.FileSystemItem;

import static pt.unips.estsetubal.tapoo.model.FileSystemItem.file;
import static pt.unips.estsetubal.tapoo.model.FileSystemItem.folder;

public class Main {

    public static void main(String[] args) {
        Tree<FileSystemItem> tree = new TreeImpl<>();

        Position<FileSystemItem> computer = tree.insert(null, folder("Computador"));
        Position<FileSystemItem> documents = tree.insert(computer, folder("Documentos"));
        Position<FileSystemItem> images = tree.insert(computer, folder("Imagens"));
        Position<FileSystemItem> classesPdf = tree.insert(documents, file("aulas.pdf"));
        tree.insert(documents, file("notas.txt"));
        tree.insert(images, file("ferias.jpg"));
        System.out.println(tree);
        System.out.println("Pai de aulas.pdf: " + tree.parent(classesPdf).element());

        System.out.println("Percurso em pré-ordem:");
        for (FileSystemItem item : tree.elements()) {
            System.out.println("- " + item);
        }

        // TODO A2.2: depois de completar a implementação, demonstrar
        // replace() e remove() de um nó folha.
    }
}
