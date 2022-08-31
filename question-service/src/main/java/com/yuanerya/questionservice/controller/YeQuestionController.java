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
import static cn.yuanerya.feign.jwt.JwtUtil.USER_NAME;


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
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        List<QuestionVO> pageList = iYeQuestionService.getPage(pageNo-1, pageSize);
        return ApiResult.success(pageList);
    }

    /**
     * 创建问题
     *
     * @param userName
     * @param dto
     * @return
     */
    @PostMapping("/create")
    public ApiResult<YeQuestion> create(@RequestHeader(value = USER_NAME) String userName,
                                        @RequestBody CreateQuestionDTO dto) {
        YeUser user = userClient.getUserByUserName(userName).getData();
        YeQuestion question = iYeQuestionService.create(dto, user);
        return ApiResult.success(question);
    }

    /**
     * 回答问题
     *
     * @param userName
     * @param question_id 需要携带当前所在问题的ID
     * @param dto
     * @return
     */
    @PostMapping("/answer/{question_id}")
    public ApiResult<YeAnswer> answer(@RequestHeader(value = USER_NAME) String userName,
                                      @PathVariable(value = "question_id") String question_id,
                                      @RequestBody AnswerAndCommentDTO dto) {
        YeUser user = userClient.getUserByUserName(userName).getData();
        YeAnswer answer = iYeAnswerService.answer(dto, user, question_id);
        return ApiResult.success(answer);
    }

    /**
     * 对 问题的回答进行评论
     *
     * @param userName
     * @param answer_id
     * @param dto
     * @return
     */
    @PostMapping("/answer/comment/{answer_id}")
    public ApiResult<YeComment> comment(@RequestHeader(value = USER_NAME) String userName,
                                        @PathVariable(value = "answer_id") String answer_id,
                                        @RequestBody AnswerAndCommentDTO dto) {
        YeUser user = userClient.getUserByUserName(userName).getData();
        YeComment comment = iYeCommentService.comment(dto, user, answer_id);
        return ApiResult.success(comment);


    }

    /**
     *删除问题
     * @param question_id
     * @return 操作成功则返回删除的问题的id
     */

    @DeleteMapping("/delete/question/{question_id}")
    public ApiResult deleteQuestion(@RequestHeader(value = USER_NAME) String userName,
                                    @PathVariable(value = "question_id") String question_id) {
        String user_id = userClient.getUserByUserName(userName).getData().getId();
        return iYeQuestionService.delete(question_id,user_id);
    }

    /**
     * 修改问题
     * @param userName
     * @param question_id
     * @param dto
     * @return
     */

    @PutMapping("/update/question/{question_id}")
    public ApiResult updateAnswer(@RequestHeader(value = USER_NAME) String userName,
                                  @PathVariable(value = "question_id") String question_id,
                                  @RequestBody CreateQuestionDTO dto
                                  ){
        String user_id = userClient.getUserByUserName(userName).getData().getId();
        return iYeQuestionService.checkAndUpdate(question_id,user_id,dto);
    }

    /**
     * 删除回答
     * @param answer_id
     * @return 操作成功则返回删除的回答的id
     */
    @DeleteMapping("/delete/answer/{answer_id}")
    public ApiResult deleteAnswer(@RequestHeader(value = USER_NAME) String userName,
                                  @PathVariable(value = "answer_id") String answer_id) {
        String user_id = userClient.getUserByUserName(userName).getData().getId();
        return iYeAnswerService.delete(answer_id,user_id);
    }


    /**
     * 修改回答
     * @param userName
     * @param answer_id
     * @param dto
     * @return
     */
    @PutMapping("/update/answer/{answer_id}")
    public ApiResult updateAnswer(@RequestHeader(value = USER_NAME) String userName,
                                  @PathVariable(value ="answer_id")  String answer_id,
                                   @RequestBody AnswerAndCommentDTO dto){
        String user_id = userClient.getUserByUserName(userName).getData().getId();
        return iYeAnswerService.checkAndUpdate(answer_id,user_id,dto);
    }


    /**
     * 删除评论
     * @param comment_id
     * @return 操作成功则返回删除的评论的id
     */
    @DeleteMapping("/delete/comment/{comment_id}")
    public ApiResult deleteComment(@RequestHeader(value = USER_NAME) String userName,
                                   @PathVariable(value = "comment_id") String comment_id) {
        String user_id = userClient.getUserByUserName(userName).getData().getId();
        return iYeCommentService.delete(comment_id,user_id);
    }

    /**\
     * 修改已经发表的评论
     * @param userName
     * @param comment_id
     * @param dto
     * @return
     */
    @PutMapping("/update/comment/{comment_id}")
    public ApiResult updateComment(@RequestHeader(value = USER_NAME) String userName,
                                   @PathVariable(value ="comment_id")  String comment_id,
                                   @RequestBody AnswerAndCommentDTO dto){
        String user_id = userClient.getUserByUserName(userName).getData().getId();
        return iYeCommentService.checkAndUpdate(comment_id,user_id,dto);
    }
    /**
     * 对外暴露，用户中远程调用查询足迹
     * 查询该用户
     * 所发布的问题，回答和评论
     * @param userName 得到用户名根据UserName再到数据库中进行查询，获取到用户ID
     * @return 返回vo
     */
    @GetMapping("/getUserAll")
    public  ApiResult<FootPrintVO> getUserQuestion(@RequestHeader(value = USER_NAME) String userName){
        String user_id = userClient.getUserByUserName(userName).getData().getId();
        FootPrintVO footPrintVO=new FootPrintVO();
        footPrintVO.setQuestion(iYeQuestionService.getMyQuestionsByUserId(user_id));
        footPrintVO.setAnswer(iYeAnswerService.getMyAnswersByUserId(user_id));
        footPrintVO.setComment(iYeCommentService.getMyCommentsByUserId(user_id));
        return ApiResult.success(footPrintVO);
    }

    /**
     * 对回答进行点赞
     * @param answer_id
     * @param userName
     * @return
     */
    @PostMapping("/tostar/{answer_id}")
    public ApiResult<Integer> tostar(@PathVariable("answer_id") String answer_id,
                                     @RequestHeader(value = USER_NAME) String userName){
        String user_id = userClient.getUserByUserName(userName).getData().getId();
        return iYeAnswerService.tostar(user_id,answer_id);
    }

    /**
     * 取消点赞
     * @param answer_id
     * @param userName
     * @return
     */

    @DeleteMapping("/removeStar/{answer_id}")
    public  ApiResult<Integer> removeStar(@PathVariable("answer_id") String answer_id,
                                          @RequestHeader(value = USER_NAME) String userName){
        String user_id = userClient.getUserByUserName(userName).getData().getId();
        return iYeAnswerService.removeStar(user_id,answer_id);
    }
}
