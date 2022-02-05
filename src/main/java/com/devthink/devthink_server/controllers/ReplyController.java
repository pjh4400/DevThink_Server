package com.devthink.devthink_server.controllers;

import com.devthink.devthink_server.application.CommentService;
import com.devthink.devthink_server.application.ReplyService;
import com.devthink.devthink_server.application.UserService;
import com.devthink.devthink_server.dto.ReplyResponseData;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/replies")
public class ReplyController {

    private final ReplyService replyService;
    private final CommentService commentService;
    private final UserService userService;

    public ReplyController(ReplyService replyService,
                           CommentService commentService,
                           UserService userService) {
        this.replyService = replyService;
        this.commentService = commentService;
        this.userService = userService;
    }

    /**
     * 모든 Reply를 조회합니다.
     * @return DB 상에 존재하는 모든 Reply 리스트
     */
    @ApiOperation(value = "전체 대댓글 조회", notes = "모든 대댓글을 조회합니다.", response = List.class)
    @GetMapping
    @ApiIgnore
    public List<ReplyResponseData> getReplies() {
        return replyService.getReplies();
    }


}
