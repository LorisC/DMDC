package com.csumb.dmdc.ParseClass;

/**
 * Created by banal_a on 3/13/15.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by banal_a on 11/29/14.
 */
public class Application extends android.app.Application {
    // Debugging switch
    public static final boolean APPDEBUG = false;
    // Debugging tag for the application
    public static final String APPTAG = "DMDC";
    // Used to pass location from MainActivity to PostActivity
    public static final String INTENT_EXTRA_LOCATION = "location";
    public static final String TAG = "MyApp";
    private static final float DEFAULT_SEARCH_DISTANCE = 250.0f;
    private static SharedPreferences preferences;
    private static ConfigHelper configHelper;
    public Application() {
    }

    public static ConfigHelper getConfigHelper() {
        return configHelper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //ParseObject.registerSubclass(Spot.class);
        ParseObject.registerSubclass(CheckList.class);
        ParseObject.registerSubclass(Family.class);
        ParseObject.registerSubclass(Feedback.class);
        ParseObject.registerSubclass(Mission.class);
        Parse.initialize(this, "x4EvKqMkdeDhCy7r3C7TlhrUhWupVijQj6dL3A5b",
                "2095PSj9B5olMyyg7e9BcFrdr8WL4MReTwb4buWP");
        //ParseInstallation.


        preferences = getSharedPreferences("com.csumb.dmdc", Context.MODE_PRIVATE);
        //client = new APIClient("8FOIKG9CPT", "e1c74e47cb346f7f25d48cf1faeca889");
        configHelper = new ConfigHelper();
        configHelper.fetchConfigIfNeeded();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true); //objects created are writable
        ParseACL.setDefaultACL(defaultACL, true);
        //ParseACL defaultACL = new ParseACL();
        // ParseACL.setDefaultACL(defaultACL, true);
    }
}

