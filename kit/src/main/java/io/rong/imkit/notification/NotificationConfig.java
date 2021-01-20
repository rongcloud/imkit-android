package io.rong.imkit.notification;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.os.Build;

import io.rong.imlib.model.Message;

public class NotificationConfig {
    private NotificationChannel mChannel;
    private PendingIntent mPendingIntent;
    private TitleType mTitleType;
    private ForegroundOtherPageAction mOtherPageAction;
    private interceptor mInterceptor;

    public NotificationConfig() {
        mTitleType = TitleType.TARGET_NAME;
        mOtherPageAction = ForegroundOtherPageAction.Sound;
    }

    public void setNotificationChannel(NotificationChannel channel) {
        this.mChannel = channel;
    }

    public void setPendingIntent(PendingIntent pendingIntent) {
        this.mPendingIntent = pendingIntent;
    }

    public void setTitleType(TitleType type) {
        this.mTitleType = type;
    }

    public void setForegroundOtherPageAction(ForegroundOtherPageAction action) {
        this.mOtherPageAction = action;
    }


    public void setInterceptor(interceptor interceptor) {
        this.mInterceptor = interceptor;
    }

    public NotificationChannel getNotificationChannel() {
        return mChannel;
    }

    public interceptor getListener() {
        return mInterceptor;
    }

    public TitleType getTitleType() {
        return mTitleType;
    }

    public PendingIntent getPendingIntent() {
        return mPendingIntent;
    }

    public ForegroundOtherPageAction getForegroundOtherPageAction() {
        return mOtherPageAction;
    }


    /**
     * 通知标题的类型
     */
    public enum TitleType {
        APP_NAME,   //应用名称
        TARGET_NAME; // 消息目标 Id 的名称
    }

    /**
     * 在前台非会话页面，接受到消息时的行为
     */
    public enum ForegroundOtherPageAction {
        Silent, //静默
        Sound,  //震动或响铃，根据系统设置决定
        Notification //弹通知，和后台时的行为一致。
    }

    public interface interceptor {
        /**
         * 是否拦截此本地通知，一般用于自定义本地通知的显示。
         * @param message 本地通知对应的消息
         * @return 是否拦截。true 拦截本地通知，SDK 不弹出通知，需要用户自己处理。false 不拦截，由 SDK 展示本地通知。
         */
        boolean isNotificationIntercepted(Message message);
        /**
         * 是否为高优先级消息。高优先级消息不受全局静默时间和会话免打扰控制，比如 @ 消息。
         *
         * @param message 接收到的消息
         * @return 是否为高优先级消息
         */
        boolean isHighPriorityMessage(Message message);

        /**
         * 注册默认 channel 之前的回调。可以通过此方法拦截并修改默认 channel 里的配置，将修改后的 channel 返回。
         *
         * @param defaultChannel 默认通知频道
         * @return 修改后的通知频道。
         */
        @TargetApi(Build.VERSION_CODES.O)
        NotificationChannel onRegisterChannel(NotificationChannel defaultChannel);
    }
}
