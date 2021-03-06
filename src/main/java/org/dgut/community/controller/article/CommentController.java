package org.dgut.community.controller.article;

import com.alibaba.fastjson.JSONObject;
import org.dgut.community.entity.ArticleComment;
import org.dgut.community.entity.User;
import org.dgut.community.resultenum.Result;
import org.dgut.community.service.article.impl.CommentServiceImpl;
import org.dgut.community.util.HttpClient;
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
@RequestMapping("/articleComment")
public class CommentController {
    private CommentServiceImpl service;
    private Map<String, Object> map = new HashMap<String, Object>();

    public CommentController(CommentServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/intercept/save/{articleId}")
    public ResponseEntity save(@PathVariable Long articleId, @RequestBody ArticleComment entity, HttpSession session){
        User user = (User) session.getAttribute("user");
        entity.setUserId(user.getUserId());

        JSONObject BaiDuC=JSONObject.parseObject(HttpClient.doPost(entity.getCommentContent()));

        String baiduC= (String) BaiDuC.get("conclusion");

        if (baiduC.equals("合规")){
            return service.save(articleId, entity);
        }else{
            return ResponseEntity.ok(ResultUtil.error(9011,"评论内容存在违规，包含低俗色情"));
        }
    }

    @DeleteMapping("/intercept/deleteById/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        return service.deleteById(id);
    }

    @PutMapping("/intercept/updateLike/{id}/{num}")
    public ResponseEntity<Result> updateLike(@PathVariable Long id, @PathVariable int num){
        return service.updateLike(id, num);
    }

    @PutMapping("/intercept/updateTop/{id}/{num}")
    public ResponseEntity<Result> updateTop(@PathVariable Long id, @PathVariable int num){
        return service.updateTop(id, num);
    }

    @GetMapping("/findByArticleId/{articleId}")
    public Page<ArticleComment> findByArticleId(@PathVariable Long articleId, @RequestParam(defaultValue = "0") int num, @RequestParam(defaultValue = "15") int size){
        Sort sort = Sort.by(Sort.Direction.ASC, "commentId");
        Pageable pageable = PageRequest.of(num, size, sort);
        return service.findByArticleId(articleId, pageable);
    }

    @GetMapping("/intercept/findCommentToUser")
    public ResponseEntity<Result> findCommentToUser(HttpSession session, @RequestParam(defaultValue = "0") int num, @RequestParam(defaultValue = "15") int size){
        Sort sort = Sort.by(Sort.Direction.DESC, "commentId");
        Pageable pageable = PageRequest.of(num, size, sort);
        User user = (User) session.getAttribute("user");
        Page<ArticleComment> comments = service.findByArticle_User_UserId(user.getUserId(), pageable);
        return ResponseEntity.ok(ResultUtil.success(comments));
    }
}
