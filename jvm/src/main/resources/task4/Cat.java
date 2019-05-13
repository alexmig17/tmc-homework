package task4;

import org.apache.log4j.Logger;

import task4.beans.Animal;

public class Cat implements Animal {

    private static final Logger LOGGER = Logger.getLogger(Cat.class);

    @Override
    public void play() {
        LOGGER.info("cat is play");
    }

    @Override
    public void voice() {
        LOGGER.info("cat voice");
    }
}
