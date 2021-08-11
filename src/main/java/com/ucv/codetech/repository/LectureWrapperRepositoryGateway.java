package com.ucv.codetech.repository;

import com.ucv.codetech.model.LectureWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class LectureWrapperRepositoryGateway {

    private final LectureWrapperRepository lectureWrapperRepository;

    public Optional<LectureWrapper> findById(Long id) {
        return lectureWrapperRepository.findById(id);
    }

    public void saveOrUpdate(LectureWrapper lectureWrapper) {
        lectureWrapperRepository.save(lectureWrapper);
    }
}
