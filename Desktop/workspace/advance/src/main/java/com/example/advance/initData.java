package com.example.advance;

import com.example.advance.common.entity.Comment;
import com.example.advance.common.entity.Post;
import com.example.advance.common.entity.User;
import com.example.advance.common.enums.UserRoleEnum;
import com.example.advance.domain.comment.repository.CommentRepository;
import com.example.advance.domain.post.repository.PostRepository;
import com.example.advance.domain.user.repository.UserRepository;
import com.example.advance.domain.user.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class initData {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @PostConstruct
    @Transactional
    public void init() {
/*
        List<User> userList =
                List.of(
                        new User("admin", passwordEncoder.encode("1234"), "user1@naver.com", UserRoleEnum.ADMIN),
                        new User("user", passwordEncoder.encode("1234"), "user2@naver.com", UserRoleEnum.NORMAL)
                );

        for (User user : userList) {
            userService.save(user);
        }
    }
*/

        User user1 = new User("admin", passwordEncoder.encode("1234"), "user1@naver.com", UserRoleEnum.ADMIN);
        User user2 = new User("user", passwordEncoder.encode("1234"), "user2@naver.com", UserRoleEnum.NORMAL);

        userRepository.save(user1);
        userRepository.save(user2);

        Post post1 = new Post("1번 게시글입니다.", user1);
        Post post2 = new Post("2번 게시글입니다.", user1);
        Post post3 = new Post("3번 게시글입니다.", user2);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        Comment comment1 = new Comment("댓글 1번", post1);
        Comment comment2 = new Comment("댓글 2번", post1);
        Comment comment3 = new Comment("댓글 3번", post2);
        Comment comment4 = new Comment("댓글 4번", post2);
        Comment comment5 = new Comment("댓글 5번", post3);
        Comment comment6 = new Comment("댓글 6번", post3);
        Comment comment7 = new Comment("댓글 7번", post3);

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        commentRepository.save(comment4);
        commentRepository.save(comment5);
        commentRepository.save(comment6);
        commentRepository.save(comment7);
    }
}
