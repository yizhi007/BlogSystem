package com.duan.blogos.web.blog;

import com.duan.blogos.dto.blog.BlogMainContentDTO;
import com.duan.blogos.dto.blog.BlogStatisticsCountDTO;
import com.duan.blogos.entity.blogger.BloggerAccount;
import com.duan.blogos.manager.properties.BloggerProperties;
import com.duan.blogos.restful.ResultBean;
import com.duan.blogos.service.audience.BlogBrowseService;
import com.duan.blogos.service.blogger.BloggerAccountService;
import com.duan.blogos.service.blogger.BloggerBlogService;
import com.duan.blogos.service.blogger.BloggerCollectBlogService;
import com.duan.blogos.service.blogger.BloggerLikeService;
import com.duan.blogos.service.common.BlogStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created on 2018/3/7.
 *
 * @author DuanJiaNing
 */
@Controller
@RequestMapping("/{bloggerName}/blog/{blogName}")
public class BlogReadPageController {

    @Autowired
    private BloggerAccountService accountService;

    @Autowired
    private BloggerBlogService blogService;

    @Autowired
    private BlogStatisticsService statisticsService;

    @Autowired
    private BloggerProperties bloggerProperties;

    @Autowired
    private BlogBrowseService blogBrowseService;

    @Autowired
    private BloggerLikeService likeService;

    @Autowired
    private BloggerCollectBlogService collectBlogService;

    @RequestMapping
    public ModelAndView page(@PathVariable String bloggerName,
                             @PathVariable String blogName) {
        ModelAndView mv = new ModelAndView();

        BloggerAccount account = accountService.getAccount(bloggerName);
        int bloggerId = account.getId();
        if (account == null) {
            mv.setViewName("error/error");
            mv.addObject("code", 6);
            mv.addObject(bloggerProperties.getSessionNameOfErrorMsg(), "博主不存在！");
            return mv;
        }

        int blogId = blogService.getBlogId(account.getId(), blogName);
        if (blogId == -1) {
            mv.setViewName("error/error");
            mv.addObject("code", 5);
            mv.addObject(bloggerProperties.getSessionNameOfErrorMsg(), "博文不存在！");
            return mv;
        }

        ResultBean<BlogMainContentDTO> mainContent = blogBrowseService.getBlogMainContent(blogId);
        ResultBean<BlogStatisticsCountDTO> statistics = statisticsService.getBlogStatisticsCount(blogId);

        mv.addObject("main", mainContent.getData());
        mv.addObject("stat", statistics.getData());

        if (likeService.getLikeState(bloggerId, blogId))
            mv.addObject("likeState", "");
        if (collectBlogService.getCollectState(bloggerId, blogId))
            mv.addObject("collectState", "");


        mv.setViewName("blogger/read_blog");
        return mv;
    }

}