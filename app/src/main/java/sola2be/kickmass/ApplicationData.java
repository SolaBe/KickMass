package sola2be.kickmass;

import com.instagram.instagramapi.objects.IGSession;

/**
 * Created by Sola2Be on 01.09.2016.
 */

public class ApplicationData {

    public static final String CLIENT_ID = "eacc077eee2d4fa685f1d75face80c72";

    public static final String CLIENT_SECRET = "5df831f64bf3454cb1ec5856b314316b";

    public static final String REDIRECT_URI = "http://sitedeveloper.com.ua/";

    public static final String AUTORIZATION_URL = "https://api.instagram.com/oauth/authorize/?client_id="
            +CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=code&scope=follower_list+relationships";

    public static IGSession session = null;
}
