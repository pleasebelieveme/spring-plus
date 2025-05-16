package org.example.expert.domain.todo.repository;

import java.util.Optional;

import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TodoQueryRepositoryImpl implements TodoQueryRepository {

	private final JPAQueryFactory queryFactory;
	private final QTodo qTodo = QTodo.todo;
	private final QUser qUser = QUser.user;

	@Override
	public Optional<Todo> findByIdWithUser(Long todoId) {
		Todo todo = queryFactory
			.selectFrom(this.qTodo)
			.leftJoin(this.qTodo.user, qUser).fetchJoin()
			.where(
				this.qTodo.id.eq(todoId)
			)
			.fetchOne();

		return Optional.ofNullable(todo);
	}
}