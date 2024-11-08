package net.biryeongtrain.destiny2mc.gun;

import com.nimbusds.oauth2.sdk.id.Identifier;

public class Gun
{
    private ElementType type;
    private Sounds Sound;


    public static class Sounds {
        Identifier fireSound;
        Identifier reloadSound;
        Identifier specialSound;
    }
}
