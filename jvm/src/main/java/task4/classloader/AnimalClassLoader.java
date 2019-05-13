package task4.classloader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * custom classloader
 */
public class AnimalClassLoader extends ClassLoader {

    private static final String FILE_LOCATION = ClassLoader.getSystemResource("task4").getPath();

    private static boolean notExist(File file) {
        return !file.exists();
    }

    @Override
    protected Class<?> findClass(String name) {
        try {
            File javaFile = getFileByName(name);
            byte[] compiledBytes = compile(name, javaFile);
            return defineClass(null, compiledBytes, 0, compiledBytes.length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private File getFileByName(String name) throws ClassNotFoundException {
        File file = new File(String.format(FILE_LOCATION + "/%s.java", name));
        if (notExist(file)) {
            throw new ClassNotFoundException();
        }
        return file;
    }

    private byte[] compile(String name, File file) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, file.getPath());
        File compiledFile = new File(String.format(FILE_LOCATION + "/%s.class", name));
        return Files.readAllBytes(compiledFile.toPath());
    }
}
