package task5.classloader;

import static java.lang.String.format;
import static task5.utils.AppUtils.getFileNameWithoutExtension;
import static task5.utils.AppUtils.getResourcePath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import task5.exception.AppRuntimeException;
import task5.exception.DynamicClassValidationException;
import task5.utils.AppUtils;

/**
 * class loader for load dynamic classes which implement LogPrintable interface
 */
public class LogPrintableClassLoader extends ClassLoader {

    public static final String RESOURCES_PREFIX = "resources:";
    private static final String CAN_LOAD_INTERFACE = "LogPrintable";
    private static final String CLASS_LOG_PRINTABLE_PATTERN = "public class %s implements " + CAN_LOAD_INTERFACE;
    private Map<String, LogPrintableClassLoader> classCache = new LinkedHashMap<>();

    /**
     * load class
     *
     * @param name class name for load
     * @return loaded Class
     * @throws ClassNotFoundException if class wasn't found
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        LogPrintableClassLoader classLoader = classCache.get(name);
        if (Objects.nonNull(classLoader)) {
            return classLoader.loadClass(name);
        }
        return super.loadClass(name);
    }

    /**
     * get list of classes which was cached
     *
     * @return List
     */
    public List<String> getCachedClassNames() {
        return new ArrayList<>(classCache.keySet());
    }

    /**
     * try load class by input path in put it into cache
     *
     * @param filePath path to file
     */
    public void addToCache(String filePath) {
        LogPrintableClassLoader classLoader = new LogPrintableClassLoader();
        classCache.put(classLoader.loadUniqueClass(filePath), classLoader);
    }

    /**
     * try load unique class.
     *
     * @param filePath input file path
     * @return loaded class name
     */
    private String loadUniqueClass(String filePath) {
        return getFileByFullPath(filePath)
                .map(this::validate)
                .map(this::compile)
                .map(this::defineClass)
                .map(Class::getName)
                .orElseThrow(AppRuntimeException::new);
    }

    /**
     * define class
     *
     * @param compiledBytes input bytes
     * @return Class
     */
    private Class<?> defineClass(byte[] compiledBytes) {
        return super.defineClass(null, compiledBytes, 0, compiledBytes.length);
    }

    /**
     * create new File by input path if it is exist
     *
     * @param path input path. Available 'resources:' prefix for get path to resources
     * @return Optional<File>
     */
    private Optional<File> getFileByFullPath(final String path) {
        String filePath = path;
        if (filePath.startsWith(RESOURCES_PREFIX)) {
            filePath = filePath.replace(RESOURCES_PREFIX, getResourcePath());
        }
        return Optional.of(new File(filePath)).filter(AppUtils::fileExist);
    }

    /**
     * validate input file.
     * Throw DynamicClassValidationException runtime exception if not valid
     *
     * @param file input file
     * @return input file
     */
    private File validate(final File file) {
        String logPrintableClass = format(CLASS_LOG_PRINTABLE_PATTERN, getFileNameWithoutExtension(file));
        boolean isLogPrintable = false;
        try (BufferedReader fileBuff = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = fileBuff.readLine()) != null) {
                String trimmedLine = line.trim();
                if (isComment(trimmedLine)) {
                    throw new DynamicClassValidationException("Comments not allowed");
                }
                if (trimmedLine.startsWith(logPrintableClass)) {
                    if (isLogPrintable) {
                        throw new DynamicClassValidationException("Not allowed two public classes");
                    }
                    isLogPrintable = true;
                }
            }
            if (!isLogPrintable) {
                throw new DynamicClassValidationException(format("Class should implement %s interface at first position", CAN_LOAD_INTERFACE));
            }
        } catch (IOException e) {
            throw new AppRuntimeException(e);
        }
        return file;
    }

    /**
     * is line start/end comment
     *
     * @param trimmedLine trimmed line
     * @return boolean
     */
    private boolean isComment(String trimmedLine) {
        return trimmedLine.startsWith("//") || trimmedLine.startsWith("/*") || trimmedLine.startsWith("*/");
    }

    /**
     * compile input file
     *
     * @param file input file
     * @return compiled bytes
     */
    private byte[] compile(File file) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, file.getPath());

        File compiledFile = new File(file.getAbsolutePath().replace(".java", ".class"));
        try {
            return Files.readAllBytes(compiledFile.toPath());
        } catch (IOException e) {
            throw new AppRuntimeException(e);
        }
    }
}
