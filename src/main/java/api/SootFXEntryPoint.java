package api;

import py4j.GatewayServer;

public class SootFXEntryPoint {

    private SootFX sootFX;

    public SootFXEntryPoint() {
        sootFX = new SootFX();
    }

    public SootFX sootFX() {
        return sootFX;
    }

    public static void main(String[] args) {
        GatewayServer gatewayServer = new GatewayServer(new SootFXEntryPoint());
        gatewayServer.start();
        System.out.println("Gateway Server Started");
    }

}