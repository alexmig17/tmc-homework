package task5.dialog.impl;

import static java.lang.String.format;
import static task5.utils.AppUtils.scanNumericFromConsole;

import org.apache.log4j.Logger;

import task5.dialog.Dialog;
import task5.services.LogPrintableService;

/**
 * dialog for make choose what do with existing class
 */
public class UpdateOrExecuteClassDialog implements Dialog {

    private final static Logger logger = Logger.getLogger(UpdateOrExecuteClassDialog.class);
    private final String className;
    private final LogPrintableService logPrintableService = LogPrintableService.getInstance();

    UpdateOrExecuteClassDialog(String className) {
        this.className = className;
    }

    /**
     * dialog for make choose what do with existing class
     */
    @Override
    public void dialog() {
        printMenu();
        boolean exit = false;
        do {
            int userChoice = scanNumericFromConsole();
            switch (userChoice) {
                case 1:
                    logPrintableService.createAndExecute(className);
                    exit = true;
                    break;
                case 2:
                    updateClassDialog(className);
                    exit = true;
                    break;
                case -1:
                    exit = true;
                    break;
                default:
                    logger.info("Can not find command. Please type '-1' for return to previous dialog or repeat.");
                    break;
            }
        } while (!exit);
    }

    /**
     * menu for update or execute class
     */
    private void printMenu() {
        logger.info("You can update or execute task:");
        logger.info("1. Execute");
        logger.info(format("2. Update %s", className));
    }

    /**
     * run dialog for update existing class
     *
     * @param className class name
     */
    private void updateClassDialog(String className) {
        UpdateExistClassDialog updateClassDialog = new UpdateExistClassDialog(className);
        updateClassDialog.dialog();
    }
}
