package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.CommentService;
import com.devthink.devthink_server.common.Error;
import com.devthink.devthink_server.common.ErrorMessage;
import com.devthink.devthink_server.domain.Comment;
import com.devthink.devthink_server.domain.Review;
import com.devthink.devthink_server.domain.User;
import com.devthink.devthink_server.dto.CommentRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 모든 Comment를 조회합니다.
     * @return
     */
    @GetMapping
    public List<Comment> getComments() {
        return commentService.getComments();
    }

    /**
     * 입력된 comment 정보로 새로운 Comment를 생성합니다.
     * @return 생성된 Comment의 id 값
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public String createComment(@RequestBody CommentRequestDto commentRequestDto){
        User user = null; //TODO: user_id 로 User 가져오기
        Review review = null; //TODO: review_id 로 Review 가져오기
        return commentService.createComment(user, review, commentRequestDto);
    }


    /**
     * commentId를 통하여 기존의 Comment를 수정합니다.
     * @return 수정된 Comment의 id 값
     */
    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateComment(@PathVariable("commentId") Long commentId, @RequestBody CommentRequestDto commentRequestDto){
        Optional<Comment> comment = commentService.getComment(commentId);
        if (comment.isEmpty()) {
            return ResponseEntity.badRequest().body(new Error(HttpStatus.BAD_REQUEST, ErrorMessage.NO_SUCH_COMMENT));
        } else {
            commentService.updateComment(commentId, commentRequestDto);
            return ResponseEntity.ok().body(comment.get().toCommentResponseDto());
        }
    }
}
