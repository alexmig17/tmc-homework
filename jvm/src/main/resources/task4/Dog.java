package task4;

import org.apache.log4j.Logger;

import task4.beans.Animal;

public class Dog implements Animal {

    private static final Logger LOGGER = Logger.getLogger(Dog.class);

    @Override
    public void play() {
        LOGGER.info("dog is play");
    }

    @Override
    public void voice() {
        LOGGER.info("dog voice");
    }
}
