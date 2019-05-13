package task5.utils;

import static java.lang.Character.isWhitespace;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.io.File;
import java.util.Scanner;

import org.apache.log4j.Logger;

/**
 * Class for store DynamicClassLoaderApp utils method
 */
public final class AppUtils {

    private final static Logger logger = Logger.getLogger(AppUtils.class);

    private AppUtils() {

    }

    /**
     * get application path for resources
     *
     * @return resource path
     */
    public static String getResourcePath() {
        return requireNonNull(ClassLoader.getSystemClassLoader().getResource("")).getPath();
    }

    /**
     * substring file extension in input file name
     *
     * @param file input file name with extension
     * @return file without extension
     */
    public static String getFileNameWithoutExtension(File file) {
        return file.getName().replaceFirst("[.][^.]+$", "");
    }

    /**
     * check is input 'cs' not blank
     *
     * @param cs input data
     * @return boolean
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * check is input 'cs' blank
     *
     * @param cs input data
     * @return boolean
     */
    private static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * replace package delimiter '.' to path delimiter '\'
     *
     * @param className class name with packages
     * @return replacement reult
     */
    public static String replacePackageDelimiterToPath(String className) {
        return className.replace(".", "\\");
    }

    /**
     * check is file exist
     *
     * @param file input file
     * @return boolean
     */
    public static boolean fileExist(File file) {
        return file.exists();
    }

    /**
     * scan numeric from console
     *
     * @return user input data
     */
    public static int scanNumericFromConsole() {
        Integer userChoice = null;
        while (isNull(userChoice)) {
            Scanner in = new Scanner(System.in);
            String inputData = in.next();
            logger.info(format("user input data %s", inputData));
            try {
                userChoice = Integer.valueOf(inputData);
            } catch (NumberFormatException e) {
                logger.info(format("Wrong input data. Required is numeric but found %s. please try again", inputData));
            }
        }
        return userChoice;
    }
}
