package io.rong.imkit.userinfo.model;

public class GroupUserInfo {
    private String mNickname;
    private String mUserId;
    private String mGroupId;

    public GroupUserInfo(String groupId, String userId, String nickname) {
        this.mGroupId = groupId;
        this.mNickname = nickname;
        this.mUserId = userId;
    }

    public String getGroupId() {
        return mGroupId;
    }

    public void setGroupId(String mGroupId) {
        this.mGroupId = mGroupId;
    }

    public String getNickname() {
        return mNickname;
    }

    public String getUserId() {
        return mUserId;
    }
}

