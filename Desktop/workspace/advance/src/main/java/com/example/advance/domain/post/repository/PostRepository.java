package com.example.advance.domain.post.repository;

import com.example.advance.common.entity.Post;
import com.example.advance.domain.comment.model.dto.PostSummaryDto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
            SELECT new com.example.advance.domain.post.model.dto.PostSummaryDto(
            p.content,
            SIZE(p.comments)
            ) FROM Post p
            WHERE p.user.username = :username
            """)
    List<PostSummaryDto> findAllWithCommentsByUsername(@Param("username") String username);

    @EntityGraph(attributePaths = {"user", "comments"})
    List<Post> findByUserUsername(String username);
}

// 디폴트 전역 설정은 지연 로딩인데
// findAllWithCommentsByUsername 메서드에서는 즉시 로딩으로 설정한 것이다.

/*
    @Query("""
    SELECT new org.example.nbcam_addvanced_1.domain.post.model.dto.PostSummaryDto(
        p.content,
        SIZE(p.comments)
    )
    FROM Post p
    WHERE p.user.username = :username
    """)
    List<PostSummaryDto> findAllWithCommentsByUsername(String username);
*/