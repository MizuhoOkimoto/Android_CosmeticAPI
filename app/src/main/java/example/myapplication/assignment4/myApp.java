package example.myapplication.assignment4;

import android.app.Application;

public class myApp extends Application {
    private NetworkingService networkingService = new NetworkingService();

    public JsonService getJsonService() {
        return jsonService;
    }

    private JsonService jsonService = new JsonService();

    //created one networking service and it doesn't matter the lifecycle
    public NetworkingService getNetworkingService() {
        return networkingService;
    }



}
