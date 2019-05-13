package task5.dialog.impl;

import static java.lang.String.format;

import org.apache.log4j.Logger;

/**
 * dialog for ask path to new class which will upload
 */
public class AddNewClassDialog extends UploadClassDialog {

    private final static Logger logger = Logger.getLogger(AddNewClassDialog.class);

    AddNewClassDialog() {
        super(null);
    }

    /**
     * dialog for ask path to new class which will upload
     */
    @Override
    public void dialog() {
        logger.info(format("please provide full path to new ..%s.java file", "Example"));
        super.dialog();
    }
}
