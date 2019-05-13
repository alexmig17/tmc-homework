package task5.dialog.impl;

import static task5.services.LogPrintableService.PredefineImpl.ONE;
import static task5.services.LogPrintableService.PredefineImpl.THREE;
import static task5.services.LogPrintableService.PredefineImpl.TWO;
import static task5.utils.AppUtils.isNotBlank;
import static task5.utils.AppUtils.scanNumericFromConsole;

import java.util.List;

import org.apache.log4j.Logger;

import task5.dialog.Dialog;
import task5.exception.AppRuntimeException;
import task5.services.LogPrintableService;

/**
 * main menu dialog
 */
public class MainMenuDialog implements Dialog {

    private static final LogPrintableService logPrintableService = LogPrintableService.getInstance();
    private final static Logger logger = Logger.getLogger(MainMenuDialog.class);
    private static final int DYNAMIC_MENU_NUMBER = 5;

    /**
     * print main application text dialog
     */
    private static void printMenu() {
        logger.info("Welcome to dynamic class Loader example application.");
        logger.info("Please choose action:");
        logger.info("1. Run current implementation.");
        logger.info("2. Load predefined implementation #2.");
        logger.info("3. Load predefined implementation #3.");
        logger.info("4. Load default implementation.");
        int menuNumber = DYNAMIC_MENU_NUMBER;
        for (String className : logPrintableService.getLogPrintableClassNames()) {
            logger.info(menuNumber + ". Sub dialog for " + className);
            menuNumber++;
        }
        logger.info(menuNumber + ", Add new class");
        logger.info("'-1' For exit.");
    }

    /**
     * execute dynamic added dialogs
     *
     * @param userChoice userChoice
     * @return boolean true if was execute, false if hasn't input command
     */
    private static boolean dynamicCommandExecute(int userChoice) {
        List<String> cachedClasses = logPrintableService.getLogPrintableClassNames();
        if (isCachedClassChoice(userChoice, cachedClasses)) {
            String className = cachedClasses.get(userChoice - DYNAMIC_MENU_NUMBER);
            updateOrExecuteClassDialog(className);
            return true;
        } else if (userChoice == cachedClasses.size() + DYNAMIC_MENU_NUMBER) {
            addNewClassDialog();
            return true;
        }
        return false;
    }

    /**
     * is user choose menu for chased class
     *
     * @param userChoice    user choice
     * @param cachedClasses list of cashed classes
     * @return boolean
     */
    private static boolean isCachedClassChoice(int userChoice, List<String> cachedClasses) {
        return userChoice >= DYNAMIC_MENU_NUMBER && userChoice < cachedClasses.size() + DYNAMIC_MENU_NUMBER;
    }

    /**
     * dialog for add new class
     */
    private static void addNewClassDialog() {
        AddNewClassDialog addNewClassDialog = new AddNewClassDialog();
        addNewClassDialog.dialog();
    }

    /**
     * dialog for make choose: execute or update class
     *
     * @param className input class name
     */
    private static void updateOrExecuteClassDialog(String className) {
        UpdateOrExecuteClassDialog updateOrExecuteClassDialog = new UpdateOrExecuteClassDialog(className);
        updateOrExecuteClassDialog.dialog();
    }

    /**
     * default error message for user and log error
     *
     * @param e input exception
     */
    private static void defaultErrorMessage(Exception e) {
        logger.error("omething going wrong.", e);
        logger.info("Something going wrong see log for find details.");
    }

    /**
     * main menu dialog
     */
    @Override
    public void dialog() {
        boolean exit = false;
        while (!exit) {
            try {
                printMenu();
                int userChoice = scanNumericFromConsole();
                switch (userChoice) {
                    case 1:
                        logPrintableService.createAndExecutePredefined();
                        break;
                    case 2:
                        logPrintableService.loadPredefinedImplementation(TWO);
                        break;
                    case 3:
                        logPrintableService.loadPredefinedImplementation(THREE);
                        break;
                    case 4:
                        logPrintableService.loadPredefinedImplementation(ONE);
                        break;
                    case -1:
                        exit = true;
                        break;
                    default:
                        if (dynamicCommandExecute(userChoice)) {
                            break;
                        }

                        logger.info("Can not find command. Please try again");
                        break;
                }
            } catch (AppRuntimeException e) {
                if (isNotBlank(e.getMessageForUser())) {
                    logger.info(e.getMessageForUser());
                    logger.info("Please try again");
                } else {
                    defaultErrorMessage(e);
                }
            } catch (Exception e) {
                defaultErrorMessage(e);
            }
        }
    }
}
