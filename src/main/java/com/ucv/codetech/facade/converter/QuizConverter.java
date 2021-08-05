package com.ucv.codetech.facade.converter;

import com.ucv.codetech.controller.model.input.AnswerDto;
import com.ucv.codetech.controller.model.input.QuestionDto;
import com.ucv.codetech.controller.model.input.QuizDto;
import com.ucv.codetech.controller.model.output.DisplayAnswerDto;
import com.ucv.codetech.controller.model.output.DisplayQuestionDto;
import com.ucv.codetech.controller.model.output.DisplayQuizDto;
import com.ucv.codetech.model.Answer;
import com.ucv.codetech.model.Question;
import com.ucv.codetech.model.Quiz;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuizConverter {

    public Quiz quizDtoToEntity(QuizDto quizDto) {
        Quiz quiz = new Quiz();
        List<Question> questions = questionDtosToEntities(quizDto.getQuestions());
        questions.forEach(quiz::addQuestion);
        return quiz;
    }

    public DisplayQuizDto entityToDisplayQuizDto(Quiz quiz) {
        DisplayQuizDto displayQuizDto = new DisplayQuizDto();
        displayQuizDto.setQuestions(entitiesToDisplayQuestionDtos(quiz.getQuestions()));
        return displayQuizDto;
    }

    private List<Question> questionDtosToEntities(List<QuestionDto> questions) {
        return questions
                .stream()
                .map(this::questionDtoToEntity)
                .collect(Collectors.toList());
    }

    private Question questionDtoToEntity(QuestionDto questionDto) {
        Question question = new Question();
        question.setQuestionContent(questionDto.getQuestion());
        List<Answer> answers = answerDtosToEntities(questionDto.getAnswers());
        answers.forEach(question::addAnswer);
        return question;
    }

    private List<Answer> answerDtosToEntities(List<AnswerDto> answers) {
        return answers
                .stream()
                .map(this::answerDtoToEntity)
                .collect(Collectors.toList());
    }

    private Answer answerDtoToEntity(AnswerDto answerDto) {
        Answer answer = new Answer();
        answer.setCorrectAnswer(answerDto.isCorrectAnswer());
        answer.setDescription(answerDto.getAnswer());
        return answer;
    }

    private List<DisplayQuestionDto> entitiesToDisplayQuestionDtos(List<Question> questions) {
        return questions
                .stream()
                .map(this::entityToDisplayQuestionDto)
                .collect(Collectors.toList());
    }

    private DisplayQuestionDto entityToDisplayQuestionDto(Question question) {
        DisplayQuestionDto displayQuestionDto = new DisplayQuestionDto();
        displayQuestionDto.setQuestion(question.getQuestionContent());
        displayQuestionDto.setId(question.getId());
        displayQuestionDto.setMultipleAnswers(question.isMultipleAnswers());
        displayQuestionDto.setAnswers(entitiesToDisplayAnswerDtos(question.getAnswers()));
        return displayQuestionDto;
    }

    private List<DisplayAnswerDto> entitiesToDisplayAnswerDtos(List<Answer> answers) {
        return answers
                .stream()
                .map(this::entityToDisplayAnswerDto)
                .collect(Collectors.toList());
    }

    private DisplayAnswerDto entityToDisplayAnswerDto(Answer answer) {
        DisplayAnswerDto displayAnswerDto = new DisplayAnswerDto();
        displayAnswerDto.setId(answer.getId());
        displayAnswerDto.setDescription(answer.getDescription());
        return displayAnswerDto;
    }
}
