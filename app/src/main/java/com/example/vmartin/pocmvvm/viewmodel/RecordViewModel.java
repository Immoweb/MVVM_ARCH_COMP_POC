package com.example.vmartin.pocmvvm.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.example.vmartin.pocmvvm.model.FoodTruckPlace;
import com.example.vmartin.pocmvvm.model.Record;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Locale;



/**
 * Created by nvandamme on 13-07-17.
 * All right reserved for Immoweb MVVM_POC
 */

public class RecordViewModel extends BaseObservable {

    private static final String STATIC_MAPS_IMAGE_SIZED_URL = "https://maps.googleapis.com/maps/api/staticmap?center=%1$s&zoom=18&size=640x320&markers=color:blue%%7C%2$s";
    private final Context mContext;
    private final Record mRecord;

    public RecordViewModel(Context context, Record record) {
        mContext = context;
        mRecord = record;
    }

    @BindingAdapter({"bind:staticMapUrl"})
    public static void loadImage(final ImageView view, final String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .into(view);
    }

    private FoodTruckPlace getdata() {
        return mRecord.getFields();
    }

    public String getEmplacement() {
        return getdata().getEmplacement();
    }

    public String getFoodTruck() {
        String foodTruckName = "No food truck today";
        switch (Calendar.getInstance(Locale.US).get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                if (!TextUtils.isEmpty(getdata().getLundi())) {
                    foodTruckName = getdata().getLundi();
                }
                break;
            case Calendar.TUESDAY:
                if (!TextUtils.isEmpty(getdata().getMardi())) {
                    foodTruckName = getdata().getMardi();
                }
                break;
            case Calendar.WEDNESDAY:
                if (!TextUtils.isEmpty(getdata().getMercredi())) {
                    foodTruckName = getdata().getMercredi();
                }
                break;
            case Calendar.THURSDAY:
                if (!TextUtils.isEmpty(getdata().getJeudi())) {
                    foodTruckName = getdata().getJeudi();
                }
                break;
            case Calendar.FRIDAY:
                if (!TextUtils.isEmpty(getdata().getVendredi())) {
                    foodTruckName = getdata().getVendredi();
                }
                break;
            case Calendar.SATURDAY:
                if (!TextUtils.isEmpty(getdata().getSamedi())) {
                    foodTruckName = getdata().getSamedi();
                }
                break;
        }
        return foodTruckName;
    }

    public String getImageUrl() {
        double lat = getdata().getCoordonneesWgs84().get(0);
        double lng = getdata().getCoordonneesWgs84().get(1);
        String urlCoordinates = lat + "," + lng;
        return String.format(STATIC_MAPS_IMAGE_SIZED_URL, urlCoordinates, urlCoordinates);
    }

    public View.OnClickListener onClickCard() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchDetailWithTransition(view);
            }
        };
    }

    private void launchDetailWithTransition(View view) {

    }

}
