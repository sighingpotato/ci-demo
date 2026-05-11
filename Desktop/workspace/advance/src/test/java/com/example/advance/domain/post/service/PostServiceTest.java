package com.example.advance.domain.post.service;

import com.example.advance.common.entity.Post;
import com.example.advance.common.entity.User;
import com.example.advance.common.enums.UserRoleEnum;
import com.example.advance.data.PostFixture;
import com.example.advance.data.UserFixture;
import com.example.advance.domain.post.model.dto.PostDto;
import com.example.advance.domain.post.repository.PostRepository;
import com.example.advance.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    public static String DEFAULT_USERNAME = "kim";

    // 게시글 생성 단위 테스트 진행

    @Test
    @DisplayName("게시글 생성 성공 - 유효한 사용자와 내용이 주어졌을 때 - 성공 케이스")
    void createPost_success() {

        // given
        String content = "테스트 게시글 입니다.";
        User testUser = UserFixture.createUserAdminRole();
        Post testPost = PostFixture.createPost1();

        when(userRepository.findUserByUsername(DEFAULT_USERNAME)).thenReturn(Optional.of(testUser));
        when(postRepository.save(any(Post.class))).thenReturn(testPost);

        // when
        PostDto result = postService.createPost(testUser.getUsername(), testPost.getContent());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(testPost.getContent());
        assertThat(result.getId()).isEqualTo(testPost.getId());
        assertThat(result.getUsername()).isEqualTo(DEFAULT_USERNAME);

        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    @DisplayName("사용자 명으로 게시글 목록 조회 - 성공")
    void getPostListByUsername_success() {

        // given
        User testUser = UserFixture.createUserAdminRole();

        List<Post> postList = List.of(
                PostFixture.createPost1(),
                PostFixture.createPost2()
        );

        testUser.getPosts().addAll(postList);

        when(userRepository.findUserByUsername(DEFAULT_USERNAME)).thenReturn(Optional.of(testUser));

        // when

        List<PostDto> result = postService.getPostListByUsername(DEFAULT_USERNAME);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(result.get(0).getContent()).isEqualTo("테스트 게시글 1");
        assertThat(result.get(1).getContent()).isEqualTo("테스트 게시글 2");
    }

    @Test
    @DisplayName("사용자 명으로 게시글 목록 조회 - 실패 - 사용자가 없는 경우")
    void getPostListByUsername_fail_case1() {

        // given
        when(userRepository.findUserByUsername(DEFAULT_USERNAME)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> postService.getPostListByUsername("kim"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("등록된 사용자가 없습니다.");

    }
}