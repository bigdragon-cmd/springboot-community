package org.dgut.community.repository.article;

import org.dgut.community.entity.ArticleReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<ArticleReply, Long> {
    Page<ArticleReply> findByComment_commentId(Long commentId, Pageable pageable);
    Page<ArticleReply> findByToUserName(String userName, Pageable pageable);
    Page<ArticleReply> findByComment_Article_User_UserId(Long userId, Pageable pageable);
}
