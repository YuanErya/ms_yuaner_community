package com.yuanerya.questionservice.controller;



import cn.yuanerya.feign.clients.UserClient;
import cn.yuanerya.feign.common.api.ApiResult;
import cn.yuanerya.feign.model.dto.AnswerAndCommentDTO;
import cn.yuanerya.feign.model.dto.CreateQuestionDTO;
import cn.yuanerya.feign.model.entity.YeAnswer;
import cn.yuanerya.feign.model.entity.YeComment;
import cn.yuanerya.feign.model.entity.YeQuestion;
import cn.yuanerya.feign.model.entity.YeUser;
import cn.yuanerya.feign.model.vo.FootPrintVO;
import cn.yuanerya.feign.model.vo.QuestionVO;
import com.yuanerya.questionservice.service.IYeAnswerService;
import com.yuanerya.questionservice.service.IYeCommentService;
import com.yuanerya.questionservice.service.IYeQuestionService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

import static cn.yuanerya.feign.jwt.JwtUtil.HEADER_STRING;


@RestController
@RequestMapping("/question")
public class YeQuestionController {
    @Resource
    private IYeQuestionService iYeQuestionService;
    @Resource
    private UserClient userClient;
    @Resource
    private IYeAnswerService iYeAnswerService;
    @Resource
    private IYeCommentService iYeCommentService;

    /**
     * 分页查询问题
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public ApiResult<List<QuestionVO>> list(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                            @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        List<QuestionVO> pageList = iYeQuestionService.getPage(pageNo, pageSize);
        return ApiResult.success(pageList);
    }

    /**
     * 创建问题
     *
     * @param token
     * @param dto
     * @return
     */
    @PostMapping("/create")
    public ApiResult<YeQuestion> create(@RequestHeader(value = HEADER_STRING) String token,
                                        @RequestBody CreateQuestionDTO dto) {
        YeUser user = userClient.checkUser(token).getData();
        YeQuestion question = iYeQuestionService.create(dto, user);
        return ApiResult.success(question);
    }

    /**
     * 回答问题
     *
     * @param token
     * @param question_id 需要携带当前所在问题的ID
     * @param dto
     * @return
     */
    @PostMapping("/answer")
    public ApiResult<YeAnswer> answer(@RequestHeader(value = HEADER_STRING) String token,
                                      @RequestHeader(value = "question_id") String question_id,
                                      @RequestBody AnswerAndCommentDTO dto) {
        YeUser user = userClient.checkUser(token).getData();
        YeAnswer answer = iYeAnswerService.answer(dto, user, question_id);
        return ApiResult.success(answer);
    }

    /**
     * 对 问题的回答进行评论
     *
     * @param token
     * @param answer_id
     * @param dto
     * @return
     */
    @PostMapping("/answer/comment")
    public ApiResult<YeComment> comment(@RequestHeader(value = HEADER_STRING) String token,
                                        @RequestHeader(value = "answer_id") String answer_id,
                                        @RequestBody AnswerAndCommentDTO dto) {
        YeUser user = userClient.checkUser(token).getData();
        YeComment comment = iYeCommentService.comment(dto, user, answer_id);
        return ApiResult.success(comment);


    }

    /**
     *删除问题
     * @param question_id
     * @return 操作成功则返回删除的问题的id
     */

    @DeleteMapping("/delete/question")
    public ApiResult deleteQuestion(@RequestHeader(value = HEADER_STRING) String token,
                                    @RequestHeader(value = "question_id") String question_id) {
        String user_id = userClient.checkUser(token).getData().getId();
        return iYeQuestionService.delete(question_id,user_id);
    }

    /**
     * 修改问题
     * @param token
     * @param question_id
     * @param dto
     * @return
     */

    @PutMapping("/update/question")
    public ApiResult updateAnswer(@RequestHeader(value = HEADER_STRING) String token,
                                  @RequestHeader(value = "question_id") String question_id,
                                  @RequestBody CreateQuestionDTO dto
                                  ){
        String user_id = userClient.checkUser(token).getData().getId();
        return iYeQuestionService.checkAndUpdate(question_id,user_id,dto);
    }

    /**
     * 删除回答
     * @param answer_id
     * @return 操作成功则返回删除的回答的id
     */
    @DeleteMapping("/delete/answer")
    public ApiResult deleteAnswer(@RequestHeader(value = HEADER_STRING) String token,
                                  @RequestHeader(value = "answer_id") String answer_id) {
        String user_id = userClient.checkUser(token).getData().getId();
        return iYeAnswerService.delete(answer_id,user_id);
    }


    /**
     * 修改回答
     * @param token
     * @param answer_id
     * @param dto
     * @return
     */
    @PutMapping("/update/answer")
    public ApiResult updateAnswer(@RequestHeader(value = HEADER_STRING) String token,
                                   @RequestHeader(value ="answer_id")  String answer_id,
                                   @RequestBody AnswerAndCommentDTO dto){
        String user_id = userClient.checkUser(token).getData().getId();
        return iYeAnswerService.checkAndUpdate(answer_id,user_id,dto);
    }


    /**
     * 删除评论
     * @param comment_id
     * @return 操作成功则返回删除的评论的id
     */
    @DeleteMapping("/delete/comment")
    public ApiResult deleteComment(@RequestHeader(value = HEADER_STRING) String token,
                                   @RequestHeader(value = "comment_id") String comment_id) {
        String user_id = userClient.checkUser(token).getData().getId();
        return iYeCommentService.delete(comment_id,user_id);
    }

    /**\
     * 修改已经发表的评论
     * @param token
     * @param comment_id
     * @param dto
     * @return
     */
    @PutMapping("/update/comment")
    public ApiResult updateComment(@RequestHeader(value = HEADER_STRING) String token,
                                   @RequestHeader(value ="comment_id")  String comment_id,
                                   @RequestBody AnswerAndCommentDTO dto){
        String user_id = userClient.checkUser(token).getData().getId();
        return iYeCommentService.checkAndUpdate(comment_id,user_id,dto);
    }
    /**
     * 获取传来的用户的token信息解析出来用户，查询该用户
     * 所发布的问题，回答和评论
     * @param token 通过Header获取到tokren,进行解析得到用户名根据UserName再到数据库中进行查询，获取到用户ID
     * @return 返回vo
     */
    @GetMapping("/getUserAll")
    public  ApiResult<FootPrintVO> getUserQuestion(@RequestHeader(value = HEADER_STRING) String token){
        String user_id = userClient.checkUser(token).getData().getId();
        FootPrintVO footPrintVO=new FootPrintVO();
        footPrintVO.setQuestion(iYeQuestionService.getMyQuestionsByUserId(user_id));
        footPrintVO.setAnswer(iYeAnswerService.getMyAnswersByUserId(user_id));
        footPrintVO.setComment(iYeCommentService.getMyCommentsByUserId(user_id));
        return ApiResult.success(footPrintVO);
    }

}
