package sola2be.kickmass;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.instagram.instagramapi.engine.InstagramAuthenticationClient;
import com.instagram.instagramapi.engine.InstagramEngine;
import com.instagram.instagramapi.exceptions.InstagramException;
import com.instagram.instagramapi.interfaces.InstagramAPIResponseCallback;
import com.instagram.instagramapi.interfaces.InstagramAPIService;
import com.instagram.instagramapi.objects.IGPagInfo;
import com.instagram.instagramapi.objects.IGUser;

import java.util.ArrayList;

public class ListFollowersActivity extends AppCompatActivity {

    private Button buttonMassUnfollow;
    private ListView listViewFollowers;
    private InstagramAPIResponseCallback<ArrayList<IGUser>> apiCallbackUsersFollow = new InstagramAPIResponseCallback<ArrayList<IGUser>>() {
        @Override
        public void onResponse(ArrayList<IGUser> responseObject, IGPagInfo pageInfo) {
            Log.d("onResponse followers",responseObject.size()+"");
            FollowersAdapter adapter = new FollowersAdapter(ListFollowersActivity.this,R.layout.list_item,responseObject);
            listViewFollowers.setAdapter(adapter);
        }

        @Override
        public void onFailure(InstagramException exception) {

        }
    };

    private InstagramAPIResponseCallback<IGUser> apiCallbackUser = new InstagramAPIResponseCallback<IGUser>() {

        @Override
        public void onResponse(IGUser responseObject, IGPagInfo pageInfo) {
            Log.d("onResponse", responseObject.getFollowsCount()+"");
            InstagramEngine.getInstance(ListFollowersActivity.this).getFollowersOfUser(apiCallbackUsersFollow,responseObject.getId());
        }

        @Override
        public void onFailure(InstagramException exception) {

        }
    };

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_followers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.logo);
        setSupportActionBar(toolbar);

        String token = getIntent().getStringExtra("token");
        buttonMassUnfollow = (Button) findViewById(R.id.buttonMassUnfollow);
        listViewFollowers = (ListView) findViewById(R.id.listFollowers);

        InstagramEngine.getInstance(this).getFollowedBy(apiCallbackUsersFollow);

        InstagramEngine.getInstance(this).getUserDetails(apiCallbackUser);
        //InstagramEngine.getInstance(this).unFollowUser();
    }


}
