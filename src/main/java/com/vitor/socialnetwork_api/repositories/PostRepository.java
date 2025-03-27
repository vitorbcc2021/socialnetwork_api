package com.vitor.socialnetwork_api.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vitor.socialnetwork_api.models.PostModel;

@Repository
public interface PostRepository extends JpaRepository<PostModel, UUID> {
    List<PostModel> findByAuthor_Id(UUID authorId);
}
