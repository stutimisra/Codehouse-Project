package com.example.backend.controller;

import com.example.backend.model.Post;
import com.example.backend.payload.ApiResponse;
import com.example.backend.payload.PostRequest;
import com.example.backend.payload.PostResponse;
import com.example.backend.repository.PostRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.CurrentUser;
import com.example.backend.security.UserPrincipal;
import com.example.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.net.URI;
@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostRequest postRequest) {
        Post post = postService.createPost(postRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{postId}")
                .buildAndExpand(post.getPostId()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "Post Create successfully"));
    }

    @GetMapping("/{postId}/comments")
    public PostResponse getCommentsByPostId(@PathVariable Long postId) {
        return postService.getCommentsByPostId(postId);
    }

    @GetMapping("/{userId}/posts")
    public List<PostResponse> getPostsByUser(@PathVariable Long userId) {
        return postService.getPostByUser(userId);
    }
}