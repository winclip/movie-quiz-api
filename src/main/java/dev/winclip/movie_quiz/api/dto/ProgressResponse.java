package dev.winclip.movie_quiz.api.dto;

import java.util.List;

public record ProgressResponse(int totalPages, int pageSize, List<PageProgressItem> pages) {
}
