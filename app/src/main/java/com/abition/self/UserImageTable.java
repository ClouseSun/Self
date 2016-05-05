package com.abition.self;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 其靖 on 2016/5/5.
 */
public class UserImageTable extends BmobObject {
    private String user_id;
    private String imageURL;

    UserImageTable(String user_id, String imageURL) {
        this.user_id = user_id;
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }
}
