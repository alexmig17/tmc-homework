package task5.dialog.impl;

import static java.lang.String.format;
import static task5.utils.AppUtils.replacePackageDelimiterToPath;

import org.apache.log4j.Logger;

/**
 * dialog for update existing class
 */
public class UpdateExistClassDialog extends UploadClassDialog {

    private final static Logger logger = Logger.getLogger(UpdateExistClassDialog.class);
    private String className;

    UpdateExistClassDialog(String className) {
        super(className);
        this.className = className;
    }

    /**
     * dialog for update existing class
     */
    @Override
    public void dialog() {
        logger.info(format("please provide full path to %s.java file", replacePackageDelimiterToPath(className)));
        super.dialog();
    }
}
