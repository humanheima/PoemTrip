package com.brotherd.poemtrip.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dumingwei on 2017/2/5.
 */
public class UserInfoBean {


    /**
     * "campus": "龙河校区",
     * "userid": "35",
     * "username": "xunao",
     * "phone": "15667676906",
     * "userpic": "http://ylbook.xun-ao.com/images/201702/hN2HcHJlxU.jpg",
     * "address": "图书馆"
     * "role_id": "2"是回收员，只能回收；3是入库员，只能入库；4出库员，只能出库；8是仓库管理员，
     * 只能入库和出库。所有的角色都可以进入用户界面。
     */

    private String userid;
    private String username;
    private String phone;
    private String userpic;
    private String address;
    private String campus;
    @SerializedName("real_name")
    private String realName;
    @SerializedName("role_id")
    private String roleId;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
