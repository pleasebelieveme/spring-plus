package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoQueryRepository {

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

    // Query 적용 전 코드
    // @Query("SELECT t FROM Todo t " +
    //         "LEFT JOIN t.user " +
    //         "WHERE t.id = :todoId")
    // Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);

    @Query("SELECT t FROM Todo t " +
        "WHERE (:from IS NULL OR t.modifiedAt >= :from) " +
        "AND (:to IS NULL OR t.modifiedAt <= :to) " +
        "AND (:weather IS NULL OR LOWER(t.weather) LIKE LOWER(CONCAT('%', :weather, '%'))) " +
        "ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByModifiedAtBetween(
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to,
        @Param("weather") String weather,
        Pageable pageable
    );
}
