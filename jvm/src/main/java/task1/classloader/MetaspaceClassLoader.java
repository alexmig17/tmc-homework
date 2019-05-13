package task1.classloader;

import java.io.File;
import java.nio.file.Files;

/**
 * classloader for create and load classes for clutter up metaspace memory propose
 */
public class MetaspaceClassLoader extends AbstractClassLoader {

    private final static String ROOT_PATH = ClassLoader.getSystemResource("temp").getPath();
    private final static String STATIC_FIELDS;
    private static final String TEMPLATE;

    static {
        String fields = "    private static final Integer %s = %d;\n";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 9000; i++) {
            stringBuilder.append(String.format(fields, "name" + i, 1));
        }
        STATIC_FIELDS = stringBuilder.toString();
    }

    static {
        try {
            File templateFile = new File(ClassLoader.getSystemResource("temp/Template.java").getFile());
            TEMPLATE = new String(Files.readAllBytes(templateFile.toPath()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getClassNamePrefix() {
        return "";
    }

    @Override
    public String getTemplateClass() {
        return TEMPLATE;
    }

    @Override
    public Object[] getTemplateArgs(String className) {
        return new String[]{className, STATIC_FIELDS};
    }

    @Override
    public String getFilePathLocation() {
        return ROOT_PATH;
    }
}
