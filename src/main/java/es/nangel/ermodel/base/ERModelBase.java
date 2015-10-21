package es.nangel.ermodel.base;

import es.nangel.ermodel.model.Entity;

/**
 * Created by nangel on 21/10/15.
 */
public final class ERModelBase {
    private static final String FIRST_TOKEN_GENERATE = "generate";

    public static void main(final String args[]) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (args.length == 3) {
            if (FIRST_TOKEN_GENERATE.equals(args[0])) {
                String[] plugins = args[1].split(",");
                for (String plugin : plugins) {
                    Class<?> entityEntryPoint;
                    if (plugin.contains(".")) {
                        entityEntryPoint = Class.forName(plugin);
                    } else {
                        entityEntryPoint = Class.forName("es.nangel.ermodel." + plugin + "." + plugin);
                    }
                    Entity entityCreator = (Entity) entityEntryPoint.newInstance();
                    for (String file : args[2].split(",")) {
                        entityCreator.process(file);
                    }
                }
            }
        }
    }
}
