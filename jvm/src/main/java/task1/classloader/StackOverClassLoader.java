package task1.classloader;

import java.io.File;
import java.nio.file.Files;

/**
 * classloader for create and load classes which has many fields in method for stack overflow propose
 */
public class StackOverClassLoader extends AbstractClassLoader {

    private static final String ROOT_PATH = ClassLoader.getSystemResource("temp").getPath();
    private final static String CLASS_NAME_PREFIX = "Stack";
    private final static String FIELDS;
    private static final String TEMPLATE;

    static {
        String fields = "        double %s = %d;\n";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 9000; i++) {
            stringBuilder.append(String.format(fields, "n" + i, i));
        }

        FIELDS = stringBuilder.toString();
    }

    static {
        try {
            File templateFile = new File(ClassLoader.getSystemResource("temp/TemplateStackOverFlow.java").getFile());
            TEMPLATE = new String(Files.readAllBytes(templateFile.toPath()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    String getClassNamePrefix() {
        return CLASS_NAME_PREFIX;
    }

    @Override
    String getTemplateClass() {
        return TEMPLATE;
    }

    @Override
    Object[] getTemplateArgs(String className) {
        return new Object[]{className, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS, FIELDS};
    }

    @Override
    String getFilePathLocation() {
        return ROOT_PATH;
    }
}
