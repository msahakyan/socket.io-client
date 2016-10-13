package com.android.msahakyan.ottonovaclient.adapter.delegates;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.msahakyan.ottonovaclient.R;
import com.android.msahakyan.ottonovaclient.model.AdapterItem;
import com.android.msahakyan.ottonovaclient.model.Command;
import com.android.msahakyan.ottonovaclient.model.ServerCommand;
import com.android.msahakyan.ottonovaclient.model.data.MapData;
import com.android.msahakyan.ottonovaclient.util.AppUtils;
import com.android.msahakyan.ottonovaclient.util.ICommandCommunicationListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author msahakyan
 */
public class MapCommandDelegate extends BaseAdapterDelegate {

    private static final String STATIC_MAPS_URL = "http://maps.google.com/maps/api/staticmap?";
    private static final int IMAGE_ZOOM = 15;

    public MapCommandDelegate(Context ctx, ICommandCommunicationListener listener) {
        super(ctx, TYPE_COMMAND_MAP, listener);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new MapCommandViewHolder((ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.list_item_map, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull List<AdapterItem> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        Command command = (Command) items.get(position);
        MapCommandViewHolder viewHolder = (MapCommandViewHolder) holder;

        MapData mapData = (MapData) command.getCommand().getData();
        String lat = mapData.getLatitude();
        String lng = mapData.getLongitude();
        loadStaticGoogleMap(lat, lng, viewHolder.mapView);
    }

    private void loadStaticGoogleMap(String latitude, String longitude, ImageView mapView) {
        // Should load marker icon from the following markerUrl and show it on the given co-ordinates
        String markerUrl = "http://tinyurl.com/2ftvtt6|" + latitude + "," + longitude;
        int screenWidth = AppUtils.getScreenSizeInPixels(getContext()).x;
        String url = STATIC_MAPS_URL + "markers=icon:" + markerUrl + "&center=" + latitude + "," + longitude +
            "&zoom=" + IMAGE_ZOOM + "&size=" + screenWidth + "x" + screenWidth;
        Picasso.with(getContext()).load(url).into(mapView);
    }

    @Override
    public boolean isForViewType(@NonNull List<AdapterItem> items, int position) {
        if (!(items.get(position) instanceof Command)) {
            return false;
        }
        Command command = (Command) items.get(position);
        return command.getCommand().getType().equals(ServerCommand.TYPE_MAP) && super.isForViewType(items, position);
    }

    static class MapCommandViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.map_view)
        ImageView mapView;

        MapCommandViewHolder(ViewGroup view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
