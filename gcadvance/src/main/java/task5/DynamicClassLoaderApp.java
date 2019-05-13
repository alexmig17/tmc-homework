package task5;

import static task5.services.LogPrintableService.PredefineImpl.ONE;

import task5.dialog.impl.MainMenuDialog;
import task5.services.LogPrintableService;

/**
 * Task 5. (15 points) Custom classloader
 * 1.	Create a console application with possibility of dynamic loading/updating development functionality.
 * 2.	Implement a custom classloader with a class-caching mechanism.
 * 3.	Keep new functionality in a specified directory. Archive it in jar.
 * 4.	The application should have console dialog to choose an option (add new/update existing class, demonstrate new functionality - invoke methods of the new class).
 * 5.	Implement the output via log4j library.
 */
public class DynamicClassLoaderApp {

    private static final LogPrintableService LOG_PRINTABLE_SERVICE = LogPrintableService.getInstance();

    /**
     * execute task 5
     *
     * @param args nor args
     */
    public static void main(String[] args) {
        LOG_PRINTABLE_SERVICE.loadPredefinedImplementation(ONE);
        MainMenuDialog mainMenuDialog = new MainMenuDialog();
        mainMenuDialog.dialog();
    }
}
