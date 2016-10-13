package com.android.msahakyan.ottonovaclient.adapter.delegates;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.android.msahakyan.ottonovaclient.R;
import com.android.msahakyan.ottonovaclient.model.AdapterItem;
import com.android.msahakyan.ottonovaclient.model.Command;
import com.android.msahakyan.ottonovaclient.model.ServerCommand;
import com.android.msahakyan.ottonovaclient.model.data.RateData;
import com.android.msahakyan.ottonovaclient.util.ICommandCommunicationListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author msahakyan
 */
public class RateCommandDelegate extends BaseAdapterDelegate {

    public RateCommandDelegate(Context ctx, ICommandCommunicationListener listener) {
        super(ctx, TYPE_COMMAND_RATE, listener);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new RateCommandViewHolder((ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.list_item_rate, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull List<AdapterItem> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        Command command = (Command) items.get(position);
        RateCommandViewHolder viewHolder = (RateCommandViewHolder) holder;

        int[] rateValues = ((RateData) command.getCommand().getData()).getRateValues();

        viewHolder.ratingBar.setMax(rateValues[1]);
        viewHolder.ratingBar.setRating(rateValues[0]);
        viewHolder.ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (listener != null) {
                listener.onRateEventUpdated((int) rating, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public boolean isForViewType(@NonNull List<AdapterItem> items, int position) {
        if (!(items.get(position) instanceof Command)) {
            return false;
        }
        Command command = (Command) items.get(position);
        return command.getCommand().getType().equals(ServerCommand.TYPE_RATE) && super.isForViewType(items, position);
    }

    static class RateCommandViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.rating_bar)
        RatingBar ratingBar;

        RateCommandViewHolder(ViewGroup view) {
            super(view);
            ButterKnife.bind(this, view);

            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
        }
    }
}
