package com.example.advance.domain.post.service;

import com.example.advance.common.entity.Post;
import com.example.advance.common.entity.User;
import com.example.advance.domain.comment.model.dto.PostSummaryDto;
import com.example.advance.domain.post.model.dto.PostDto;
import com.example.advance.domain.post.repository.PostRepository;
import com.example.advance.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostDto createPost(String username, String content) {

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));

       Post post = postRepository.save(new Post(content, user));

        return PostDto.from(post);
    }

    // 지연 로딩이다
    // 실질적으로 사용할 때 불러오는 것

    // 즉시 로딩으로 테스트 진행
    // 유저를 조회하자 마자 조회를 할때 연관된 모든 것들을 다 불러오는 것

    public List<PostDto> getPostListByUsername(String username) {

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));

        List<Post> postList = user.getPosts();

/*
        List<PostDto> postDtoList = new ArrayList<>();

        for(Post post : postList) {
            postDtoList.add(PostDto.from(post));
        }
*/

        // post List를 postDto List로 변환
        return postList.stream()
                .map(PostDto::from)
                .collect(Collectors.toList());
    }

    public List<PostSummaryDto> getPostListDetailByUsername(String username) {

/*
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));

        List<PostSummaryDto> result = new ArrayList<>();

        for (Post post : user.getPosts()) {
            int commentCount = post.getComments().size();
            result.add(new PostSummaryDto(post.getContent(), commentCount));
        }
        return result;
*/

/*
        List<Post> postList = postRepository.findAllWithCommentsByUsername(username);

        return postList.stream()
                .map(post -> new PostSummaryDto(post.getContent(), post.getComments().size()))
                .toList();
*/

/*
        List<Post> postList = postRepository.findByUserUsername(username);

        return postList.stream()
                .map(post -> new PostSummaryDto(post.getContent(), post.getComments().size()))
                .toList();
*/

        List<PostSummaryDto> result = postRepository.findAllWithCommentsByUsername(username);
        return result;
    }
}
