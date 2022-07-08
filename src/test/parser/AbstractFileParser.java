/*
 * zguan@mun.ca
 * Student Number: 202191382
 */

package test.parser;

import java.io.File;
import java.util.Scanner;

public abstract class AbstractFileParser<T> {
    private final String filename;
    private final File file;
    private final Scanner scanner;

    public AbstractFileParser(String filename) throws FileUnreadableException {
        this.filename = filename;
        this.file = new File(filename);

        try {
            if (!file.isFile() || !file.canRead()) {
                throw new FileUnreadableException();
            }
            this.scanner = new Scanner(file);
        }
        catch (Throwable t) {
            throw new FileUnreadableException();
        }
    }

    public abstract T parse();

    protected File getFile() {
        return file;
    }

    protected String getFilename() {
        return filename;
    }

    protected Scanner getScanner() {
        return scanner;
    }

    public static class FileUnreadableException extends Exception {
        public FileUnreadableException() {
            super("Target file does not exist, or is unable to read.");
        }
    }

}
