package com.android.msahakyan.ottonovaclient.adapter.delegates;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.android.msahakyan.ottonovaclient.R;
import com.android.msahakyan.ottonovaclient.model.AdapterItem;
import com.android.msahakyan.ottonovaclient.model.Command;
import com.android.msahakyan.ottonovaclient.model.ServerCommand;
import com.android.msahakyan.ottonovaclient.model.data.DateData;
import com.android.msahakyan.ottonovaclient.util.AppUtils;
import com.android.msahakyan.ottonovaclient.util.ICommandCommunicationListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author msahakyan
 */
public class DateCommandDelegate extends BaseAdapterDelegate {

    public DateCommandDelegate(Context ctx, ICommandCommunicationListener listener) {
        super(ctx, TYPE_COMMAND_DATE, listener);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new DateCommandViewHolder((ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.list_item_date, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull List<AdapterItem> items, int position, @NonNull RecyclerView.ViewHolder holder) {
        Command command = (Command) items.get(position);
        DateCommandViewHolder viewHolder = (DateCommandViewHolder) holder;

        String date = ((DateData) command.getCommand().getData()).getTimestamp();
        String[] daysOfWeek = AppUtils.getWeekDaysWithStartedDate(date);

        for (String aDaysOfWeek : daysOfWeek) {
            addDateButtonToContainer(viewHolder, aDaysOfWeek);
        }
    }

    /**
     * Creating days of week button dynamically
     *
     * @param holder The {@link DateCommandViewHolder}
     * @param s      The button text
     */
    private void addDateButtonToContainer(DateCommandViewHolder holder, String s) {
        LinearLayout container = holder.container;
        Button button = new Button(getContext(), null, android.R.attr.buttonBarButtonStyle);
        button.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        button.setText(s);
        button.setTextSize(11.f);

        button.setOnClickListener((v) -> {
            if (listener != null) {
                listener.onWeekDaySelected(s, holder.getAdapterPosition());
            }
        });

        Resources res = Resources.getSystem();
        LayoutParams layoutParams = new LayoutParams(AppUtils.dpToPixels(res, 50), LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(layoutParams);
        container.addView(button);
    }

    @Override
    public boolean isForViewType(@NonNull List<AdapterItem> items, int position) {
        if (!(items.get(position) instanceof Command)) {
            return false;
        }
        Command command = (Command) items.get(position);
        return command.getCommand().getType().equals(ServerCommand.TYPE_DATE) && super.isForViewType(items, position);
    }

    static class DateCommandViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.date_button_container)
        LinearLayout container;

        DateCommandViewHolder(ViewGroup view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
