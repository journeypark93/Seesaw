package com.example.seesaw.repository;

import com.example.seesaw.model.TroubleComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TroubleCommentRepository extends JpaRepository<TroubleComment, Long> {

    List<TroubleComment> findAllByNickname(String nickname);

    Page<TroubleComment> findAllByTroubleIdOrderByLikeCountDesc(Long troubleId, Pageable pageable);

    List<TroubleComment> findAllByTroubleId(Long id);

    long countByTroubleId(Long id);
}
