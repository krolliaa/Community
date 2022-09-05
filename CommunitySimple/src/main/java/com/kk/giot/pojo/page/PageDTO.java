package com.kk.giot.pojo.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kk.giot.pojo.DiscussPost;
import com.kk.giot.pojo.DiscussPostAndUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO {
    private Long total;
    private List<DiscussPostAndUser> discussPosts;
    private Page<DiscussPost> page;
}
