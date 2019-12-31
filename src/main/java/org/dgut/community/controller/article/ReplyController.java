package org.dgut.community.controller.article;

import org.dgut.community.entity.ArticleComment;
import org.dgut.community.entity.ArticleReply;
import org.dgut.community.entity.User;
import org.dgut.community.resultenum.Result;
import org.dgut.community.service.article.impl.ReplyServiceImpl;
import org.dgut.community.util.ResultUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/articleReply")
public class ReplyController {
    private ReplyServiceImpl service;
    private Map<String, Object> map = new HashMap<String, Object>();

    public ReplyController(ReplyServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/intercept/save/{commentId}")
    public ResponseEntity<Result> save(@PathVariable Long commentId, @RequestBody ArticleReply entity, HttpSession session){
        User user = (User) session.getAttribute("user");
        entity.setFromUserName(user.getUserName());
        return service.save(commentId, entity);
    }

    @DeleteMapping("/intercept/deleteById/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        return service.deleteById(id);
    }

    @GetMapping("/findByCommentId/{commentId}")
    public Page<ArticleReply> findByCommentId(@PathVariable Long commentId, @RequestParam(defaultValue = "0") int num, @RequestParam(defaultValue = "15") int size){
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(num, size, sort);
        return service.findByCommentId(commentId, pageable);
    }

    @GetMapping("/intercept/findReplyToUser")
    public ResponseEntity<Result> findReplyToUser(HttpSession session, @RequestParam(defaultValue = "0") int num, @RequestParam(defaultValue = "15") int size){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(num, size, sort);
        User user = (User) session.getAttribute("user");
        Page<ArticleReply> replies = service.findByToUserName(user.getUserName(), pageable);
        return ResponseEntity.ok(ResultUtil.success(replies));
    }

    @GetMapping("/intercept/findReplyToArticle")
    public ResponseEntity<Result> findReplyToArticle(HttpSession session, @RequestParam(defaultValue = "0") int num, @RequestParam(defaultValue = "15") int size){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(num, size, sort);
        User user = (User) session.getAttribute("user");
        Page<ArticleReply> replies = service.findByComment_Article_User_UserId(user.getUserId(), pageable);
        return ResponseEntity.ok(ResultUtil.success(replies));
    }
}
