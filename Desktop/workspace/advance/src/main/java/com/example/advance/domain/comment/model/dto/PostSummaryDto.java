package com.example.advance.domain.comment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostSummaryDto {

    private String content; // 게시글의 내용
    private int commentCount; // 게시글에 달린 댓글의 수
}
