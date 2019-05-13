package task5.common;

import org.apache.log4j.Logger;

public class LogPrintableImpl implements LogPrintable {

    private final static Logger logger = Logger.getLogger(LogPrintableImpl.class);

    @Override
    public void print() {
        logger.info("********");
        logger.info("standard implementation v3");
        logger.info("********");
    }
}
