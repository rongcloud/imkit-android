package io.rong.imkit.userinfo;


import io.rong.imkit.userinfo.db.model.Group;
import io.rong.imkit.userinfo.db.model.GroupMember;
import io.rong.imlib.model.UserInfo;

public class UserDataDelegate implements UserDataProvider.UserInfoProvider, UserDataProvider.GroupInfoProvider, UserDataProvider.GroupUserInfoProvider {
    private UserDataProvider.UserInfoProvider mUserInfoProvider;
    private UserDataProvider.GroupInfoProvider mGroupInfoProvider;
    private UserDataProvider.GroupUserInfoProvider mGroupUserInfoProvider;

    UserDataDelegate() {
    }

    public void setUserInfoProvider(UserDataProvider.UserInfoProvider provider) {
        mUserInfoProvider = provider;
    }

    public void setGroupInfoProvider(UserDataProvider.GroupInfoProvider provider) {
        mGroupInfoProvider = provider;
    }

    public void setGroupUserInfoProvider(UserDataProvider.GroupUserInfoProvider provider) {
        mGroupUserInfoProvider = provider;
    }

    @Override
    public UserInfo getUserInfo(String userId) {
        if (mUserInfoProvider != null) {
            return mUserInfoProvider.getUserInfo(userId);
        }
        return null;
    }

    @Override
    public GroupMember getGroupUserInfo(String groupId, String userId) {
        if (mGroupUserInfoProvider != null) {
            return mGroupUserInfoProvider.getGroupUserInfo(groupId, userId);
        }
        return null;
    }

    @Override
    public Group getGroupInfo(String groupId) {
        if (mGroupInfoProvider != null) {
            return mGroupInfoProvider.getGroupInfo(groupId);
        }
        return null;
    }

}
