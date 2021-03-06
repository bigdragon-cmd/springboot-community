package org.dgut.community.service.article;

import org.dgut.community.entity.FourmArticle;
import org.dgut.community.resultenum.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFourm {

    FourmArticle findByArticleId(Long articleId, Long myId);

    Page<FourmArticle> findByArticleContentLike(Long userId, String articleContent, Pageable pageable);

    Page<FourmArticle> findAll(Long userId, Pageable pageable);

    Page<FourmArticle> findByUserId(Long id, Pageable pageable);

    ResponseEntity<?> deleteById(Long id);

    FourmArticle updateById(Long id, FourmArticle newFourmArticle);

//    FourmArticle updateLike(Long id, int num);

    ResponseEntity<Result> save(FourmArticle fourmArticle, Long id);
}
