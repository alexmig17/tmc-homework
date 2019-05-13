package task4;

import java.util.ArrayList;
import java.util.List;

import task4.beans.Animal;
import task4.classloader.AnimalClassLoader;

/**
 * class with implemented task4:
 * Create a new abstract class (or interface) Animal with methods: &quot;Play&quot;, &quot;Voice&quot;.
 * <p>
 * 2. Inherit Cat and Dog from the Animal class.
 * 3. Compile the classes.
 * 4. Create your own ClassLoader by extension of the standard ClassLoader.
 * 5. Load the classes Cat and Dog into an array or collection Animals by your own class
 * loader and run the methods &quot;Play&quot;, &quot;Voice&quot;.
 * 6. Output data to console by log4j library (logger).getAnimals
 */
public class LoadClassesByCustomClassloader extends ClassLoader {

    private final static ClassLoader CLASS_LOADER = new AnimalClassLoader();
    private final static Class DOG;
    private final static Class CAT;

    static {
        try {
            DOG = CLASS_LOADER.loadClass("Dog");
            CAT = CLASS_LOADER.loadClass("Cat");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * main method for run task4
     *
     * @param args no custom args
     */
    public static void main(String[] args) throws Exception {
        getAnimals().forEach(Animal::play);
        getAnimals().forEach(Animal::voice);
        Thread.sleep(1000);
    }

    /**
     * @return new dog
     * @throws Exception
     */
    private static Animal newDog() throws Exception {
        return (Animal) DOG.newInstance();
    }

    /**
     * @return new cat
     * @throws Exception
     */
    private static Animal newCat() throws Exception {
        return (Animal) CAT.newInstance();
    }

    /**
     * get list of animals
     *
     * @return list of animals
     * @throws Exception
     */
    private static List<Animal> getAnimals() throws Exception {
        List<Animal> animals = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            animals.add(i % 2 == 0 ? newDog() : newCat());
        }
        return animals;
    }
}
