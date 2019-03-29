package com.example.admin.widget;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.ArrayList;

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<Shop> shops = new ArrayList<>();

    Intent intent;

    private DatabaseReference mainNode;

    private DatabaseReference childNode;



    /*
    This method displays enrolled classes of logged in user. Widget will be empty if user is not enrolled in any class yet.
    Also note that : in order to see data in widget, user has to login and has to enrol in atleast one class.
    Recommended access of widget would be when user is logged in.
     */
    public void getAllShops(ValueEventListener listener) {
        getAllObjects(getShopsDatabase(), listener);
    }

    private void getAllObjects(DatabaseReference database, ValueEventListener listener) {
        database.orderByKey().addListenerForSingleValueEvent(listener);
    }

    private DatabaseReference getShopsDatabase() {
        return FirebaseDatabase.getInstance().getReference("Shops");
    }

    private void initializeData() throws NullPointerException {

        try {

            shops.clear();

         //   mainNode = FirebaseDatabase.getInstance().getReference("Shops");

            getAllShops(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot shopSnapshot : dataSnapshot.getChildren()) {
                        shops.add(shopSnapshot.getValue(Shop.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public WidgetDataProvider(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    @Override
    public void onCreate() {
        initializeData();

    }

    @Override
    public void onDataSetChanged() {
        initializeData();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.d("TAG", "Total count is " + shops.size());
        return shops.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_enrolled_list_item);
        remoteViews.setTextViewText(R.id.txtShopName, shops.get(i).getName());

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
