package com.lovely4k.backend.question.controller;

import com.lovely4k.backend.common.ApiResponse;
import com.lovely4k.backend.member.Sex;
import com.lovely4k.backend.question.controller.request.AnswerQuestionRequest;
import com.lovely4k.backend.question.controller.request.AnsweredQuestionParamRequest;
import com.lovely4k.backend.question.controller.request.CreateQuestionFormRequest;
import com.lovely4k.backend.question.service.QuestionService;
import com.lovely4k.backend.question.service.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/v1/questions")
@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/daily")
    public ResponseEntity<ApiResponse<DailyQuestionResponse>> getDailyQuestion(@RequestParam("coupleId") Long coupleId) {
        return ApiResponse.ok(questionService.findDailyQuestion(coupleId));
    }

    @PostMapping("/question-forms")
    public ResponseEntity<ApiResponse<CreateQuestionFormResponse>> createQuestionForm(@RequestBody @Valid CreateQuestionFormRequest request, @RequestParam("userId") Long userId, @RequestParam("coupleId") Long coupleId) {
        return ApiResponse.created("/v1/questions/question-forms",
                1L,
                questionService.createQuestionForm(request.toServiceDto(), coupleId, userId));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateQuestionResponse>> createQuestion(@RequestParam("coupleId") Long coupleId) {
        return ApiResponse.created("/v1/questions/question-forms",
                1L,
                questionService.createQuestion(coupleId));
    }

    @PatchMapping("/{id}/answers")
    public ResponseEntity<ApiResponse<Void>> answerQuestion(@PathVariable("id") Long id, @RequestParam("sex") Sex sex, @RequestBody @Valid AnswerQuestionRequest request) {
        questionService.updateQuestionAnswer(id, sex, request.choiceNumber());
        return ApiResponse.ok();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<AnsweredQuestionResponse>> getAnsweredQuestions(@ModelAttribute @Valid AnsweredQuestionParamRequest params) {
        return ApiResponse.ok(questionService.findAllAnsweredQuestionByCoupleId(params.getId(), params.getCoupleId(), params.getLimit()));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<ApiResponse<QuestionDetailsResponse>> getQuestionDetails(@PathVariable("id") Long id) {
        return ApiResponse.ok(questionService.findQuestionDetails(id));
    }

}