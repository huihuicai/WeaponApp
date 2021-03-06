package com.weapon.joker.app.message.office;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.weapon.joker.app.message.BR;
import com.weapon.joker.app.message.R;
import com.weapon.joker.lib.mvvm.command.Action0;
import com.weapon.joker.lib.mvvm.command.ReplyCommand;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/**
 * <pre>
 *     author : xiaweizi
 *     class  : com.weapon.joker.app.message.office.OfficeViewModel
 *     e-mail : 1012126908@qq.com
 *     time   : 2017/10/18
 *     desc   :
 * </pre>
 */

public class OfficeViewModel extends OfficeContact.ViewModel {

    /**
     * WeaponApp 官方客服账号
     */
    private static final String OFFICE_SERVICE = "WeaponApp";
    /**
     * 消息发送的内容
     */
    @Bindable
    public String sendContent;

    public void setSendContent(String sendContent) {
        this.sendContent = sendContent;
        notifyPropertyChanged(BR.sendContent);
    }

    public void init() {
        for (int i = 0; i < 4; i++) {
            MessageItemViewModel send = new MessageItemViewModel();
            send.type = MessageItemViewModel.MSG_SEND;
            send.content = "send:\t" + i;
            items.add(send);
        }

        for (int i = 0; i < 2; i++) {
            MessageItemViewModel receiver = new MessageItemViewModel();
            receiver.type = MessageItemViewModel.MSG_RECEIVER;
            receiver.content = "receiver:\t" + i;
            items.add(receiver);
        }
    }


    /**
     * 给官方客服发送文本消息
     */
    public void sendMessageToOffice(View view) {
        if (TextUtils.isEmpty(sendContent)) {
            Toast.makeText(getContext(), "发送的内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Logger.t("Office").i("content:\t" + sendContent);
        MessageItemViewModel send = new MessageItemViewModel();
        send.type = MessageItemViewModel.MSG_SEND;
        send.content = sendContent;
        items.add(send);
        setSendContent("");
//        Conversation conversation = Conversation.createSingleConversation(OFFICE_SERVICE);
//        Message      message      = conversation.createSendTextMessage(sendContent);
//        message.setOnSendCompleteCallback(new BasicCallback() {
//            @Override
//            public void gotResult(int status, String desc) {
//                if (status == 0) {
//                    // 消息发送成功
//                } else {
//                    // 消息发送失败
//                    Toast.makeText(getContext(), desc, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        JMessageClient.sendMessage(message);
    }


    public final ReplyCommand onRefreshCommand = new ReplyCommand(new Action0() {
        @Override
        public void call() {
            // 下拉刷新
        }
    });


    public final ObservableList<MessageItemViewModel>                     items      = new ObservableArrayList<>();
    public final BindingRecyclerViewAdapter.ItemIds<MessageItemViewModel> itemIds
            = new BindingRecyclerViewAdapter.ItemIds<MessageItemViewModel>() {
        @Override
        public long getItemId(int position, MessageItemViewModel item) {
            return position;
        }
    };

    public final OnItemBind<MessageItemViewModel> multiItems = new OnItemBind<MessageItemViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, MessageItemViewModel item) {
            if (item.type == MessageItemViewModel.MSG_SEND) {
                itemBinding.set(BR.msgModel, R.layout.item_office_msg_send);
            } else {
                itemBinding.set(BR.msgModel, R.layout.item_office_msg_receiver);
            }
        }
    };
}
