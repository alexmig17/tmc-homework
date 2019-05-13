package task5.dialog.impl;

import static java.lang.String.format;
import static task5.classloader.LogPrintableClassLoader.RESOURCES_PREFIX;
import static task5.utils.AppUtils.getResourcePath;
import static task5.utils.AppUtils.isNotBlank;
import static task5.utils.AppUtils.replacePackageDelimiterToPath;

import java.util.Scanner;

import org.apache.log4j.Logger;

import task5.dialog.Dialog;
import task5.services.LogPrintableService;

/**
 * dialog for upload class
 */
public class UploadClassDialog implements Dialog {

    private static final String EXIT_INPUT = "exit";
    private final static Logger logger = Logger.getLogger(UploadClassDialog.class);
    private final String className;
    private LogPrintableService logPrintableService = LogPrintableService.getInstance();

    public UploadClassDialog(String className) {
        this.className = className;
    }

    /**
     * validate input path
     *
     * @param path      input path
     * @param className class name
     * @return validation error
     */
    private static String validatePath(String path, String className) {
        String validPathPostfix;
        if (isNotBlank(className)) {
            validPathPostfix = replacePackageDelimiterToPath(className).concat(".java");
        } else {
            validPathPostfix = ".java";
        }
        String validationError;
        if (path.startsWith(RESOURCES_PREFIX) || path.contains(getResourcePath())) {
            validationError = "Path is not valid";
        } else if (!path.endsWith(validPathPostfix)) {
            validationError = format("wrong path. Path should end by %s", validPathPostfix);
        } else {
            validationError = null;
        }
        return validationError;
    }

    /**
     * dialog for upload class
     */
    @Override
    public void dialog() {
        String inputData;
        boolean exit = false;
        do {
            Scanner in = new Scanner(System.in);
            inputData = in.next();
            logger.info(format("user input data %s", inputData));
            String error;
            if (EXIT_INPUT.equals(inputData)) {
                exit = true;
            } else if ((error = validatePath(inputData, className)) != null) {
                logger.info(format("Error: %s", error));
                logger.info(format("please try again or type '%s' for return to main dialog", EXIT_INPUT));
            } else {
                logPrintableService.addToCache(inputData);
                exit = true;
            }
        } while (!exit);
    }
}
