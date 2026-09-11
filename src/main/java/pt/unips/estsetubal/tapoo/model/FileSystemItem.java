package pt.unips.estsetubal.tapoo.model;

import java.util.Objects;

/** Elemento armazenado na árvore que representa uma pasta ou um ficheiro. */
public final class FileSystemItem {

    public enum Type {
        FOLDER, FILE
    }

    private final String name;
    private final Type type;

    public FileSystemItem(String name, Type type) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("O nome não pode estar vazio.");
        }
        this.name = name;
        this.type = Objects.requireNonNull(type, "O tipo não pode ser null.");
    }

    public static FileSystemItem folder(String name) {
        return new FileSystemItem(name, Type.FOLDER);
    }

    public static FileSystemItem file(String name) {
        return new FileSystemItem(name, Type.FILE);
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public boolean isFolder() {
        return type == Type.FOLDER;
    }

    @Override
    public String toString() {
        return name;
    }
}
