package sola2be.kickmass;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.instagram.instagramapi.engine.InstagramAuthenticationClient;
import com.instagram.instagramapi.engine.InstagramEngine;
import com.instagram.instagramapi.exceptions.InstagramException;
import com.instagram.instagramapi.interfaces.InstagramAPIResponseCallback;
import com.instagram.instagramapi.interfaces.InstagramAPIService;
import com.instagram.instagramapi.objects.IGPagInfo;
import com.instagram.instagramapi.objects.IGRelationship;
import com.instagram.instagramapi.objects.IGUser;

import java.util.ArrayList;
import java.util.List;

public class ListFollowersActivity extends AppCompatActivity {

    private ListView listViewFollowers;
    private FollowersAdapter adapter;
    private ArrayList<IGUser> listFollowBy;
    private TextView textCountFollowers;
    private InstagramAPIResponseCallback<ArrayList<IGUser>> apiCallbackUsersFollowBy = new InstagramAPIResponseCallback<ArrayList<IGUser>>() {
        @Override
        public void onResponse(ArrayList<IGUser> responseObject, IGPagInfo pageInfo) {
            Log.d("onResponse follow by ",responseObject.size()+"");
//            adapter = new FollowersAdapter(ListFollowersActivity.this,R.layout.list_item,responseObject);
//            listViewFollowers.setAdapter(adapter);
            listFollowBy = responseObject;
            InstagramEngine.getInstance(ListFollowersActivity.this).getUsersIFollow(apiCallbackUsersFollow);
        }

        @Override
        public void onFailure(InstagramException exception) {

        }
    };

    private InstagramAPIResponseCallback<ArrayList<IGUser>> apiCallbackUsersFollow = new InstagramAPIResponseCallback<ArrayList<IGUser>>() {


        @Override
        public void onResponse(ArrayList<IGUser> responseObject, IGPagInfo pageInfo) {
            Log.d("onResponse followers",responseObject.size()+"");
            List<IGUser> finalList = new ArrayList<>();
            int countFollowBy = listFollowBy.size();
            int countFollowers = responseObject.size();
            int size = Math.min(countFollowBy,countFollowers);
            for (int i = 0; i < size; i++) {
                if (listFollowBy.get(i).getId() == responseObject.get(i).getId())
                    finalList.add(responseObject.get(i));
            }
            textCountFollowers.setText(String.valueOf(finalList.size()));
            adapter = new FollowersAdapter(ListFollowersActivity.this,R.layout.list_item,finalList);
            listViewFollowers.setAdapter(adapter);
        }

        @Override
        public void onFailure(InstagramException exception) {

        }
    };

    private InstagramAPIResponseCallback<IGUser> apiCallbackUser = new InstagramAPIResponseCallback<IGUser>() {

        @Override
        public void onResponse(IGUser responseObject, IGPagInfo pageInfo) {
            //Log.d("onResponse", responseObject.getFollowsCount()+"");
            //InstagramEngine.getInstance(ListFollowersActivity.this).getFollowersOfUser(apiCallbackUsersFollow,responseObject.getId());
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
        toolbar.setLogo(R.mipmap.ic_logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        String token = getIntent().getStringExtra("token");

        listViewFollowers = (ListView) findViewById(R.id.listFollowers);
        textCountFollowers = (TextView) findViewById(R.id.countFollowers);
        InstagramEngine.getInstance(this).getFollowedBy(apiCallbackUsersFollowBy);

       // InstagramEngine.getInstance(this).getUserDetails(apiCallbackUser);
        //InstagramEngine.getInstance(this).unFollowUser();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }

    public void onMassUnfollow(View view) {
        for (final IGUser user :
                adapter.getList()) {
            InstagramEngine.getInstance(this).unFollowUser(new InstagramAPIResponseCallback<IGRelationship>() {

                @Override
                public void onResponse(IGRelationship responseObject, IGPagInfo pageInfo) {
                    int index = -1;
                    for (int i = 0; i < adapter.getList().size(); i++) {
                        if (adapter.getList().get(i).getId().equals(user.getId())) {
                            index = i;
                        }
                    }
                    adapter.getList().remove(index);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(InstagramException exception) {

                }
            },user.getId());
        }
    }
}
