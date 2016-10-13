package com.android.msahakyan.ottonovaclient.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.msahakyan.ottonovaclient.R;
import com.android.msahakyan.ottonovaclient.adapter.ChatAdapter;
import com.android.msahakyan.ottonovaclient.application.SocketIOApp;
import com.android.msahakyan.ottonovaclient.common.CustomLayoutManager;
import com.android.msahakyan.ottonovaclient.model.Command;
import com.android.msahakyan.ottonovaclient.model.Message;
import com.android.msahakyan.ottonovaclient.network.CommandDataDeserializer;
import com.android.msahakyan.ottonovaclient.network.Event;
import com.android.msahakyan.ottonovaclient.util.AppUtils;
import com.android.msahakyan.ottonovaclient.util.ICommandCommunicationListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import timber.log.Timber;

/**
 * @author msahakyan
 */

public class ChatFragment extends BaseFragment implements ICommandCommunicationListener {

    private static final String KEY_USERNAME = "key_username";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_COMMAND = "command";

    @Bind(R.id.welcome_view)
    TextView welcomeView;

    @Bind(R.id.insert_message_view)
    EditText insertMessageView;

    @Bind(R.id.content_recycler)
    RecyclerView chatItemsRecycler;

    private Socket socket;
    private ChatAdapter chatAdapter;
    private String username;
    private boolean connected;

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance(String username) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(KEY_USERNAME, username);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getArguments().getString(KEY_USERNAME);
        initSocket();
        initAdapter();
        setHasOptionsMenu(true);
    }

    private void initAdapter() {
        chatAdapter = new ChatAdapter(mActivity, new ArrayList<>(), this);
    }

    private void initSocket() {
        socket = SocketIOApp.getInstance().getSocket();

        // Register Event Listeners
        socket.on(Socket.EVENT_CONNECT, onSocketConnected);
        socket.on(Socket.EVENT_DISCONNECT, onSocketDisconnected);
        socket.on(Socket.EVENT_CONNECT_ERROR, onSocketConnectionError);
        socket.on(Socket.EVENT_CONNECT_TIMEOUT, onSocketConnectionError);

        // Register Custom Event Listeners
        socket.on(Event.MESSAGE, onReceiveMessage);
        socket.on(Event.COMMAND, onReceiveCommand);
        socket.connect();
    }

    private void scrollToBottom() {
        chatItemsRecycler.scrollToPosition(chatAdapter.getItemCount() - 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        welcomeView.setText(Html.fromHtml(getString(R.string.user_greetings, username)));
        initRecyclerView();
        mActivity.setTitle(getString(R.string.app_name));
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new CustomLayoutManager(mActivity);
        chatItemsRecycler.setLayoutManager(layoutManager);
        chatItemsRecycler.setAdapter(chatAdapter);
        chatItemsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!chatItemsRecycler.getItemAnimator().isRunning()) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private Emitter.Listener onSocketConnected = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if (mActivity != null) {
                mActivity.runOnUiThread(() -> {
                    if (!connected) {
                        if (mActivity != null && username != null)
                            Toast.makeText(mActivity,
                                R.string.message_connected, Toast.LENGTH_LONG).show();
                        connected = true;
                    }
                });
            }
        }
    };

    private Emitter.Listener onSocketDisconnected = args -> {
        if (mActivity != null) {
            mActivity.runOnUiThread(() -> {
                connected = false;
                Toast.makeText(mActivity.getApplicationContext(),
                    R.string.message_disconnected, Toast.LENGTH_LONG).show();
            });
        }
    };

    private Emitter.Listener onSocketConnectionError = args ->
        mActivity.runOnUiThread(() ->
            Toast.makeText(mActivity, R.string.message_connection_error, Toast.LENGTH_LONG).show());

    private Emitter.Listener onReceiveMessage = args -> {
        if (mActivity != null) {
            mActivity.runOnUiThread(() -> {
                JSONObject data = (JSONObject) args[0];
                Gson gson = new Gson();
                try {
                    Message msgObj = gson.fromJson(data.toString(), Message.class);
                    showMessageOnBoard(msgObj);
                } catch (JsonSyntaxException e) {
                    Timber.w("Json parsing exception ", e);
                }
            });
        }
    };

    private Emitter.Listener onReceiveCommand = args -> {
        if (mActivity != null) {
            mActivity.runOnUiThread(() -> {
                JSONObject data = (JSONObject) args[0];
                try {
                    Gson gson = new GsonBuilder().registerTypeAdapter(Command.class, new CommandDataDeserializer()).create();
                    Command commandObj = gson.fromJson(data.toString(), Command.class);
                    ChatFragment.this.showCommandOnBoard(commandObj);
                } catch (JsonSyntaxException e) {
                    Timber.w("Json parsing exception ", e);
                }
            });
        }
    };

    private void showCommandOnBoard(Command command) {
        chatAdapter.add(command);
        scrollToBottom();
    }

    private void showMessageOnBoard(Message msg) {
        chatAdapter.add(msg);
        scrollToBottom();
    }

    private void showMessageOnBoard(String username, String message) {
        Message msg = new Message();
        msg.setAuthor(username);
        msg.setMessage(message);
        chatAdapter.add(msg);
        scrollToBottom();
    }

    private void sendMessage() {
        if (username == null) {
            return;
        }
        if (!socket.connected()) {
            return;
        }

        String message = insertMessageView.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            insertMessageView.requestFocus();
            return;
        }

        showMessageOnBoard(username, message);

        // perform the sending message.
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_AUTHOR, username);
            jsonObject.put(KEY_MESSAGE, message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit(Event.MESSAGE, jsonObject);

        // Clear edit text message
        insertMessageView.setText("");
        // Hide virtual keyboard
        AppUtils.hideKeyboard(mActivity);
    }

    @OnClick(R.id.action_send_message)
    public void onSendButtonClick() {
        sendMessage();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (mActivity != null) {
            mActivity.showMenuItems(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item_generate:
                generateRandomServerCommand();
                break;
            case R.id.action_complete:
                generateCommandComplete();
                break;
            case R.id.action_map:
                generateCommandMap();
                break;
            case R.id.action_date:
                generateCommandDate();
                break;
            case R.id.action_rate:
                generateCommandRate();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void generateCommandRate() {
        // no-option
    }

    private void generateCommandDate() {
        // no-option
    }

    private void generateCommandMap() {
        // no-option
    }

    private void generateCommandComplete() {
        // no-option
    }

    private void generateRandomServerCommand() {
        sendCommand(null);
    }

    private void sendCommand(Command command) {
        if (username == null) {
            return;
        }
        if (!socket.connected()) {
            return;
        }

        // perform the sending command.
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_AUTHOR, username);
            jsonObject.put(KEY_COMMAND, command != null ? command : new Command());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit(Event.COMMAND, jsonObject);
    }

    @Override
    public void onRateEventUpdated(int value, int position) {
        Message msg = prepareMessage(username, getString(R.string.selected_value, value));
        notifyAdapterItemUpdated(msg, position);
    }

    @Override
    public void onWeekDaySelected(String day, int position) {
        Message msg = prepareMessage(username, getString(R.string.selected_day, day));
        notifyAdapterItemUpdated(msg, position);
    }

    @Override
    public void onCheckmarkSelected(String value, int position) {
        Message msg = prepareMessage(username, getString(R.string.selected_checkmark, value));
        notifyAdapterItemUpdated(msg, position);
    }

    private void notifyAdapterItemUpdated(Message msg, int position) {
        if (msg != null) {
            new Handler(Looper.getMainLooper()).post(() ->
                chatAdapter.update(position, msg));
        }
    }

    private Message prepareMessage(String author, String message) {
        if (TextUtils.isEmpty(author)) {
            Timber.w("Author of the message is empty -- skipping");
            return null;
        }
        Message msg = new Message();
        msg.setAuthor(username);
        msg.setMessage(message);

        return msg;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (socket != null && socket.connected()) {
            Toast.makeText(mActivity, R.string.action_disconnected, Toast.LENGTH_SHORT).show();
            socket.disconnect();

            // Unregister Event Listeners
            socket.off(Socket.EVENT_CONNECT, onSocketConnected);
            socket.off(Socket.EVENT_DISCONNECT, onSocketDisconnected);
            socket.off(Socket.EVENT_CONNECT_ERROR, onSocketConnectionError);
            socket.off(Socket.EVENT_CONNECT_TIMEOUT, onSocketConnectionError);

            // Unregister Custom Event Listeners
            socket.off(Event.COMMAND, onReceiveCommand);
            socket.off(Event.MESSAGE, onReceiveMessage);
        }
    }
}

