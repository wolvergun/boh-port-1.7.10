package net.mcreator.boh;

/** Server-safe lifecycle hooks; ClientProxy adds the client registration events. */
public class CommonProxy {

    public boolean isClient() {
        return false;
    }

    public void preInit() {}

    public void init() {}

    public void postInit() {}
}
