package task5.services;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

import java.util.List;
import java.util.Optional;

import task5.classloader.LogPrintableClassLoader;
import task5.common.LogPrintable;
import task5.exception.AppRuntimeException;

/**
 * service for load LogPrintable classes and execute it objects
 */
public class LogPrintableService {

    private static final String PREDEFINED_CLASS_NAME = "task5.common.LogPrintableImpl";
    private static final String PREDEFINE_IMPLEMENTATION_PATH_PATTERN = "resources:task5/v%d/task5/common/LogPrintableImpl.java";
    private static final LogPrintableClassLoader CLASS_LOADER = new LogPrintableClassLoader();
    private static final LogPrintableService INSTANCE = new LogPrintableService();

    private LogPrintableService() {
    }

    /**
     * method for get existing singleton
     *
     * @return LogPrintableService singleton
     */
    public static final LogPrintableService getInstance() {

        return INSTANCE;
    }

    /**
     * execute if object is LogPrintable
     *
     * @param object object
     */
    private static void executeLogPrintable(Optional<?> object) {
        object.filter(LogPrintable.class::isInstance).
                map(LogPrintable.class::cast)
                .ifPresent(LogPrintable::print);
    }

    /**
     * load class by name and create new instance
     *
     * @param className class name
     * @return loaded object
     */
    private static Optional getNewInstance(String className) {
        try {
            return ofNullable(CLASS_LOADER.loadClass(className).newInstance());
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new AppRuntimeException(e);
        }
    }

    /**
     * load predefined version of LogPrintable class
     *
     * @param predefine
     */
    public void loadPredefinedImplementation(PredefineImpl predefine) {
        addToCache(format(PREDEFINE_IMPLEMENTATION_PATH_PATTERN, predefine.ordinal() + 1));
    }

    /**
     * load class by path and add it into cache
     *
     * @param filePath class file path
     */
    public void addToCache(String filePath) {
        CLASS_LOADER.addToCache(filePath);
    }

    /**
     * crate new instance by class name and execute it
     *
     * @param className class name
     */
    public void createAndExecute(String className) {
        executeLogPrintable(getNewInstance(className));
    }

    /**
     * create new Instance of predefined class
     */
    public void createAndExecutePredefined() {
        executeLogPrintable(getNewInstance(PREDEFINED_CLASS_NAME));
    }

    /**
     * get list of LogPrintable classes name
     *
     * @return List
     */
    public List<String> getLogPrintableClassNames() {
        return CLASS_LOADER.getCachedClassNames();
    }

    public enum PredefineImpl {
        ONE,
        TWO,
        THREE
    }
}
