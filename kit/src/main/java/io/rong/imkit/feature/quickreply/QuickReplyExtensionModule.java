package io.rong.imkit.feature.quickreply;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import io.rong.imkit.R;
import io.rong.imkit.config.RongConfigCenter;
import io.rong.imkit.conversation.extension.IExtensionModule;
import io.rong.imkit.conversation.extension.RongExtension;
import io.rong.imkit.conversation.extension.RongExtensionViewModel;
import io.rong.imkit.conversation.extension.component.emoticon.IEmoticonTab;
import io.rong.imkit.conversation.extension.component.plugin.IPluginModule;
import io.rong.imkit.feature.destruct.DestructManager;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

public class QuickReplyExtensionModule implements IExtensionModule {
    @Override
    public void onInit(Context context, String appKey) {

    }

    @Override
    public void onAttachedToExtension(Fragment fragment, final RongExtension extension) {
        final Conversation.ConversationType type = extension.getConversationType();
        final IQuickReplyProvider provider = RongConfigCenter.featureConfig().getQuickReplyProvider();
        if(DestructManager.isActive()) {
            return;
        }
        if(provider != null && provider.getPhraseList(type) != null && provider.getPhraseList(type).size() > 0
            && fragment != null && fragment.getContext() != null) {
            final RongExtensionViewModel rongExtensionViewModel = new ViewModelProvider(fragment).get(RongExtensionViewModel.class);
            RelativeLayout attachContainer = extension.getContainer(RongExtension.ContainerType.ATTACH);
            attachContainer.removeAllViews();
            View quickReplyIcon = LayoutInflater.from(fragment.getContext()).inflate(R.layout.rc_ext_quick_reply_icon, attachContainer, false);
            attachContainer.addView(quickReplyIcon);
            attachContainer.setVisibility(View.VISIBLE);
            quickReplyIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RelativeLayout boardContainer = extension.getContainer(RongExtension.ContainerType.BOARD);
                    boardContainer.removeAllViews();
                    QuickReplyBoard quickReplyBoard = new QuickReplyBoard(v.getContext(), boardContainer, provider.getPhraseList(type));
                    quickReplyBoard.setAttachedConversation(extension);
                    boardContainer.addView(quickReplyBoard.getRootView());
                    boardContainer.setVisibility(View.VISIBLE);
                    rongExtensionViewModel.setSoftInputKeyBoard(false);
                }
            });
        }
    }

    @Override
    public void onDetachedFromExtension() {

    }

    @Override
    public void onReceivedMessage(Message message) {

    }

    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        return null;
    }

    @Override
    public List<IEmoticonTab> getEmoticonTabs() {
        return null;
    }

    @Override
    public void onDisconnect() {

    }
}
