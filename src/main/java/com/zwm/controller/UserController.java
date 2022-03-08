package com.zwm.controller;

import com.zwm.annotation.LoginRequired;
import com.zwm.entity.User;
import com.zwm.service.impl.FollowServiceImpl;
import com.zwm.service.impl.LikeServiceImpl;
import com.zwm.service.impl.UserServiceImpl;
import com.zwm.util.CommunityUtils;
import com.zwm.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static com.zwm.util.CommunityConstantTwo.ENTITY_TYPE_USER;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.update}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeServiceImpl likeService;

    @Autowired
    private FollowServiceImpl followService;

    @LoginRequired
    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "/site/setting";
    }

    @LoginRequired
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model) {
        //如果上传文件为空就返回
        if (headerImage == null) {
            model.addAttribute("error", "您还没有选择图片!");
            return "/site/setting";
        }
        //获取图片文件名
        String fileName = headerImage.getOriginalFilename();
        //获取图片文件名后缀
        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        //检测后缀是否符合图片格式
        if (!StringUtils.isBlank(suffix) || ".jpg".equals(suffix) || ".png".equals(suffix) || ".jpeg".equals(suffix)) {
            String uploadFileName = CommunityUtils.generateUUID() + suffix;
            //存放文件到服务器中
            File uploadToServerFile = new File(uploadPath + uploadFileName);
            try {
                headerImage.transferTo(uploadToServerFile);
            } catch (IOException e) {
                logger.error("上传文件失败: " + e.getMessage());
                throw new RuntimeException("上传文件失败,服务器发生异常!", e);
            }
            //跟新用户头像URL地址
            //1. 获取当前线程的用户
            User user = hostHolder.getUser();
            int id = user.getId();
            //2. 制作当前上传头像的URL地址
            //http://localhost/community/user/header/xxx.jpg
            String headerUrl = domain + contextPath + "/user/header/" + uploadFileName;
            //3. 更新用户的头像URL地址
            userService.updateUserHeaderUrl(id, headerUrl);
            return "redirect:/index";
        } else {
            model.addAttribute("error", "文件格式不正确！请选择：jpg png jpeg 格式文件！");
            return "/site/setting";
        }
    }

    //获取头像，访问头像URL地址 ---> RESTFUL 形式 ---> 从 response 中返回回去
    @RequestMapping(value = "/header/{fileName}", method = RequestMethod.GET)
    public void getUserHeader(@PathVariable(value = "fileName") String fileName, HttpServletResponse httpServletResponse) {
        OutputStream outputStream = null;
        FileInputStream fileInputStream = null;
        try {
            //获取后缀名
            httpServletResponse.setContentType("image/" + fileName.substring(fileName.lastIndexOf(".") + 1));
            outputStream = httpServletResponse.getOutputStream();
            fileInputStream = new FileInputStream(uploadPath + fileName);
            int b = 0;
            byte[] bytes = new byte[1024];
            while ((b = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, b);
            }
        } catch (FileNotFoundException e) {
            logger.error("读取头像失败: " + e.getMessage());
        } catch (IOException e) {
            logger.error("读取头像失败: " + e.getMessage());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    logger.error("读取头像失败: " + e.getMessage());
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("读取头像失败: " + e.getMessage());
                }
            }
        }
    }

    @RequestMapping(value = "/profile/{userId}", method = RequestMethod.GET)
    public String getProfilePage(@PathVariable(value = "userId") int userId, Model model) {
        //判断该 userId 的用户是否存在，防止恶意用户
        User user = userService.findUserById(userId);
        if (user == null) throw new RuntimeException("该用户不存在");
        model.addAttribute("user", user);
        //获取当前用户收到的点赞数量
        int likeCount = likeService.findUserLikeNumbers(userId);
        model.addAttribute("likeCount", likeCount);
        //判断该用户是否已经被关注 ---> 用户是 3
        boolean hasFollowed = false;
        if (hostHolder.getUser() != null) {
            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, userId);
        }
        model.addAttribute("hasFollowed", hasFollowed);
        //传递粉丝数量
        long followerCount = followService.followerCount(ENTITY_TYPE_USER, user.getId());
        model.addAttribute("followerCount", followerCount);
        //传递关注数量
        long followeeCount = followService.followeeCount(user.getId(), ENTITY_TYPE_USER);
        model.addAttribute("followeeCount", followeeCount);
        return "/site/profile";
    }
}