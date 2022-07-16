package services.interfaces;

import configs.EntityManagerSingleton;

public interface Closeable {

    default void exit() {
        EntityManagerSingleton.getInstance().close();
    }
}
