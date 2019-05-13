package task1.classloader;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * abstract class loader for generate class by template
 */
public abstract class AbstractClassLoader extends ClassLoader {

    private static boolean fileIsNotCreated(File file) throws IOException {
        return !file.createNewFile();
    }

    private static boolean notExist(File file) {
        return !file.exists();
    }

    abstract String getClassNamePrefix();

    abstract String getTemplateClass();

    abstract Object[] getTemplateArgs(String className);

    abstract String getFilePathLocation();

    @Override
    protected Class<?> findClass(String name) {
        try {
            String className = getClassNamePrefix() + name;
            File javaFile = getFileByName(className);
            writeContentByTemplate(className, javaFile.toURI());
            byte[] compiledBytes = compile(className, javaFile);
            return defineClass(null, compiledBytes, 0, compiledBytes.length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private File getFileByName(String name) throws IOException {
        File file = new File(String.format(getFilePathLocation() + "/%s.java", name));
        if (notExist(file) && fileIsNotCreated(file)) {
            throw new RuntimeException("Can not create new file");
        }
        return file;
    }

    private void writeContentByTemplate(String className, URI uri) throws IOException {
        String classContent = String.format(getTemplateClass(), getTemplateArgs(className));
        Files.write(Paths.get(uri), classContent.getBytes());
    }

    private byte[] compile(String name, File file) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, file.getPath());
        File compiledFile = new File(String.format(getFilePathLocation() + "/%s.class", name));
        return Files.readAllBytes(compiledFile.toPath());
    }
}
