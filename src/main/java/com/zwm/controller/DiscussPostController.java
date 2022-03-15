package com.zwm.controller;

import com.zwm.dao.DiscussPostMapper;
import com.zwm.dao.elasticsearch.DiscussPostRepository;
import com.zwm.entity.Comment;
import com.zwm.entity.DiscussPost;
import com.zwm.entity.Page;
import com.zwm.entity.User;
import com.zwm.service.impl.CommentServiceImpl;
import com.zwm.service.impl.DiscussPostServiceImpl;
import com.zwm.service.impl.LikeServiceImpl;
import com.zwm.service.impl.UserServiceImpl;
import com.zwm.util.CommunityUtils;
import com.zwm.util.HostHolder;
import com.zwm.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

import static com.zwm.util.CommunityConstantTwo.ENTITY_TYPE_COMMENT;
import static com.zwm.util.CommunityConstantTwo.ENTITY_TYPE_POST;

@Controller
@RequestMapping(value = "/discuss", method = RequestMethod.GET)
public class DiscussPostController {

    @Autowired
    DiscussPostServiceImpl discussPostService;

    @Autowired
    SensitiveFilter sensitiveFilter;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private LikeServiceImpl likeService;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private DiscussPostRepository discussPostRepository;

    @RequestMapping(value = "/testES")
    public void testES() {
        System.out.println(discussPostMapper.selectDiscussPostById(241));
        System.out.println(discussPostMapper.selectDiscussPostById(242));
        System.out.println(discussPostMapper.selectDiscussPostById(243));
    }


    @RequestMapping(value = "/select1")
    public Object findDiscussPosts() {
        return discussPostService.findDiscussPosts(1, 0, 10);
    }

    @RequestMapping(value = "/select2")
    public Object findDiscussPostsCount() {
        return discussPostService.findDiscussPostsCount(1);
    }

    @ResponseBody
    @RequestMapping(value = "/getCookie", method = RequestMethod.GET)
    public String sendCookie(HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie("code", CommunityUtils.generateUUID());
        cookie.setPath("/community/discussPost");
        cookie.setMaxAge(60 * 10);
        httpServletResponse.addCookie(cookie);
        return "add Cookie!";
    }

    @RequestMapping(value = "/sendCookieToServer", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue(value = "code") String code) {
        return "get Cookie: code ---> " + code;
    }

    @RequestMapping(value = "/setSession", method = RequestMethod.GET)
    @ResponseBody
    public String testSession(HttpSession httpSession) {
        //Session 比 Cookie 强大，它可以设置任何类型的数据
        httpSession.setAttribute("id", 888);
        httpSession.setAttribute("name", "wow");
        return "Set Session Ok";
    }

    @RequestMapping(value = "/getSession", method = RequestMethod.GET)
    @ResponseBody
    public String testSession(@CookieValue(value = "JSESSIONID") String JSESSIONID) {
        return "Get Session OK: " + JSESSIONID;
    }

    @RequestMapping(value = "/testSensitive", method = RequestMethod.GET)
    public String testSensitiveWord() {
        String filterTest = sensitiveFilter.filter("赌+++++++++++++博好啊赌博好");
        return filterTest;
    }

    @RequestMapping(value = "/ajax", method = RequestMethod.POST)
    public String testAjax(String name, Integer age, String text) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("age", age);
        map.put("text", text);
        return CommunityUtils.getJsonString(0, "SendSuccess", map);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addDiscussPost(String title, String content) {
        //获取当前线程的账号信息
        User user = hostHolder.getUser();
        //如果当前没有登录用户返回未登录错误信息
        if (user == null) {
            return CommunityUtils.getJsonString(403, "你还没有登录哦！");
        }
        System.out.println(title);
        System.out.println(content);
        DiscussPost discussPost = new DiscussPost();
        discussPost.setUserId(user.getId());
        discussPost.setTitle(title);
        discussPost.setContent(content);
        discussPost.setCreateTime(new Date());
        discussPostService.addDiscussPost(discussPost);
        // 报错的情况,将来统一处理.
        return CommunityUtils.getJsonString(0, "发布成功!");
    }

    @RequestMapping(path = "/detail/{discussPostId}", method = RequestMethod.GET)
    public String getDiscussPost(@PathVariable(value = "discussPostId") int discussPostId, Model model, Page page) {
        //System.out.println("start：" + page.getStart());
        //根据 ID 查询
        DiscussPost discussPost = discussPostService.selectDiscussPost(discussPostId);
        model.addAttribute("post", discussPost);
        //查询该帖子的用户信息
        User user = userService.findUserById(discussPost.getUserId());
        model.addAttribute("user", user);
        //获取该帖子的赞的数量
        long likeCount = likeService.findLikeNumbers(ENTITY_TYPE_POST, discussPostId);
        //如果当前用户不为空，验证状态，是否是已点赞的状态
        int likeStatus = hostHolder.getUser() == null ? 0 : likeService.findLikeStatus(hostHolder.getUser().getId(), ENTITY_TYPE_POST, discussPostId);
        model.addAttribute("likeCount", likeCount);
        model.addAttribute("likeStatus", likeStatus);
        //设置一页显示多少条数据 ---> 这里为 5 条
        page.setLimit(5);
        //设置查询路径
        page.setPath("/discuss/detail/" + discussPostId);
        //设置数据总数
        page.setRows(discussPost.getCommentCount());
        //首先获取帖子评论，帖子评论的标志为 entityType = 1;
        //这里可以把它抽象出来，放到常量类，这样可以直接使用常量做到见名知意
        //帖子评论类型？是哪个帖子的？显示第几条到第几条数据？
        List<Comment> commentList = commentService.findCommentsByEntity(ENTITY_TYPE_POST, discussPostId, page.getStart(), page.getLimit());
        List<Map<String, Object>> commentMapList = new ArrayList<>();
        //下面找出回复帖子评论的评论：
        if (commentList != null) {
            for (Comment comment : commentList) {
                //每个帖子评论都有所属用户，需要把用户信息和评论信息放到Map中传回给前端
                Map<String, Object> discussPostCommentMap = new HashMap<>();
                discussPostCommentMap.put("comment", comment);
                discussPostCommentMap.put("user", userService.findUserById(comment.getUserId()));
                //将帖子评论的赞放入map中以便前端获取数据
                long discussPostCommentLikeCount = likeService.findLikeNumbers(ENTITY_TYPE_COMMENT, comment.getId());
                int discussPostCommentLikeStatus = hostHolder.getUser() == null ? 0 : likeService.findLikeStatus(hostHolder.getUser().getId(), ENTITY_TYPE_COMMENT, comment.getId());
                discussPostCommentMap.put("likeCount", discussPostCommentLikeCount);
                discussPostCommentMap.put("likeStatus", discussPostCommentLikeStatus);
                //整个帖子评论的的回复数量为
                int replyCount = commentService.findCountByEntity(ENTITY_TYPE_COMMENT, comment.getId());
                discussPostCommentMap.put("replyCount", replyCount);
                //也将帖子数量这一特性放入资源包中
                //从每一个回复帖子的评论中可以获取到该帖子评论的 id 值
                //所以可以从这每一个 id 值中再获取回复该评论的评论
                List<Comment> replyCommentsCommentList = commentService.findCommentsByEntity(ENTITY_TYPE_COMMENT, comment.getId(), 0, Integer.MAX_VALUE);
                //评论的评论作为一个整体，并且每条评论也有自己的信息和发布人，所以需要把评论的评论放入一个整体
                List<Map<String, Object>> replyCommentsCommentMapList = new ArrayList<>();
                //遍历评论的评论
                if (replyCommentsCommentList != null) {
                    for (Comment replyCommentsComment : replyCommentsCommentList) {
                        Map<String, Object> commentCommentMap = new HashMap<>();
                        commentCommentMap.put("reply", replyCommentsComment);
                        //回复评论的用户
                        commentCommentMap.put("user", userService.findUserById(replyCommentsComment.getUserId()));
                        //回复的目标
                        User target = replyCommentsComment.getTargetId() == 0 ? null : userService.findUserById(replyCommentsComment.getTargetId());
                        commentCommentMap.put("target", target);
                        //将帖子评论的赞放入map中以便前端获取数据
                        long replyCommentsCommentLikeCount = likeService.findLikeNumbers(ENTITY_TYPE_COMMENT, replyCommentsComment.getId());
                        int replyCommentsCommentLikeStatus = hostHolder.getUser() == null ? 0 : likeService.findLikeStatus(hostHolder.getUser().getId(), ENTITY_TYPE_COMMENT, replyCommentsComment.getId());
                        commentCommentMap.put("likeCount", replyCommentsCommentLikeCount);
                        commentCommentMap.put("likeStatus", replyCommentsCommentLikeStatus);
                        replyCommentsCommentMapList.add(commentCommentMap);
                    }
                }
                //现在replyCommentsCommentMapList已经存放了该帖子下边评论的评论各种信息 ---> 已形成一个完整的资源包
                //还需要把这个资源包跟帖子的资源包进行配对，形成一个更大的资源包
                //这样就形成了，每个 Map 都包含：用户-帖子评论-帖子评论的评论
                discussPostCommentMap.put("replys", replyCommentsCommentMapList);
                //将其放到专门放资源包的集合当中
                commentMapList.add(discussPostCommentMap);
            }
        }
        model.addAttribute("comments", commentMapList);
        return "/site/discuss-detail";
    }
}
