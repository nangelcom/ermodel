package es.nangel.ermodel.base;

import es.nangel.ermodel.model.Entity;
import es.nangel.ermodel.parser.Parser;

/**
 * Created by nangel on 21/10/15.
 */
public final class ERModelBase {
    private static final String FIRST_TOKEN_GENERATE = "generate";

    public static void main(final String args[]) throws Exception {
        if (args.length == 3) {
            if (FIRST_TOKEN_GENERATE.equals(args[0])) {
                String[] plugins = args[1].split(",");
                for (String plugin : plugins) {
                    Entity entityCreator = getInstanceWithNamespace(plugin, "model", Entity.class);
                    for (String file : args[2].split(",")) {
                        String[] engine = file.split("\\.");
                        Parser parser = getInstanceWithNamespace(engine[engine.length - 1], "parser", Parser.class);
                        parser.parse(file);
                        entityCreator.process(file);
                    }
                }
            }
        }
    }

    private static <T> T getInstanceWithNamespace(String className, String type, Class<T> tClass) throws Exception {
        String classNamePath = className;
        if (!className.contains(".")) {
            classNamePath = "es.nangel.ermodel." + type + "." + className + "." + classNamePath;
        }
        return getInstanceForClass(classNamePath, tClass);
    }

    private static <T> T getInstanceForClass(String className, Class<T> tClass) throws Exception {
        return (T) loadClass(className).newInstance();
    }

    private static Class<?> loadClass(String className) throws Exception {
        return Class.forName(className);
    }
}
