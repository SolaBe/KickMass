package sola2be.kickmass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.instagram.instagramapi.engine.InstagramEngine;
import com.instagram.instagramapi.exceptions.InstagramException;
import com.instagram.instagramapi.interfaces.InstagramAPIResponseCallback;
import com.instagram.instagramapi.objects.IGPagInfo;
import com.instagram.instagramapi.objects.IGRelationship;
import com.instagram.instagramapi.objects.IGUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sola2Be on 05.09.2016.
 */

public class FollowersAdapter extends ArrayAdapter<IGUser> {


    private List<IGUser> userList;
    private Context context;
    private LayoutInflater inflater;
    private UnfollowInterface mInterface;
    private static String removeId;
    private InstagramAPIResponseCallback<IGRelationship> callback =  new InstagramAPIResponseCallback<IGRelationship>() {
        @Override
        public void onResponse(IGRelationship responseObject, IGPagInfo pageInfo) {
            int index = -1;
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getId().equals(removeId)) {
                    index = i;
                }
            }
            userList.remove(index);
            notifyDataSetChanged();
            mInterface.onUserUnfollow(userList.size());
        }

        @Override
        public void onFailure(InstagramException exception) {

        }
    };
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            removeId = (String) view.getTag();
            InstagramEngine.getInstance(context).unFollowUser(callback, (String) view.getTag());
        }
    };

    public FollowersAdapter(Context context, int resource, List<IGUser> objects) {
        super(context, resource, objects);
        this.userList = objects;
        this.context = context;
        inflater = LayoutInflater.from(context);
        mInterface = (UnfollowInterface) context;
    }

    class VHolder {
        private ImageView imageUser;
        private TextView textName;
        private TextView textDescr;
        private ImageView imageUnfollow;
    }

    public List<IGUser> getList() {
        return userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        IGUser user = userList.get(position);
        VHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item,null);
            holder = new VHolder();
            holder.imageUser = (ImageView) view.findViewById(R.id.imageViewUser);
            holder.textName = (TextView) view.findViewById(R.id.textViewUserName);
            holder.textDescr = (TextView) view.findViewById(R.id.textViewUserDescription);
            holder.imageUnfollow = (ImageView) view.findViewById(R.id.imageViewUnfollow);
            view.setTag(holder);
        }
        else
            holder = (VHolder) view.getTag();
        Picasso.with(context).load(user.getProfilePictureURL()).into(holder.imageUser);
        holder.textName.setText(user.getUsername());
        holder.textDescr.setText(user.getFullName());
        holder.imageUnfollow.setTag(user.getId());
        holder.imageUnfollow.setOnClickListener(listener);
        return view;
    }
}
