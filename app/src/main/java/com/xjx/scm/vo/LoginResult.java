package com.xjx.scm.vo;

import com.xjx.scm.network.BaseResult;

/**
 * 登录结果
 */
public class LoginResult extends BaseResult<LoginResult.Login> {
    public static class Login {
        private int userId;

        private String accessToken;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }

}
