package com.kk.giot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscussPostAndUser {
    //帖子列表单项是由帖子实体 + 用户构成的，所以这里封装成：DiscussPostAndUser
    private DiscussPost post;
    private User user;
}
