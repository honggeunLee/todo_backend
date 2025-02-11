package org.example.todo_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.todo_backend.dto.TagCreateRequestDto;
import org.example.todo_backend.dto.TagDto;
import org.example.todo_backend.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    @Operation(summary = "새로운 태그 생성", description = "새로운 태그를 생성합니다.")
    @PostMapping
    public ResponseEntity<TagDto> createTag(@Validated @RequestBody TagCreateRequestDto dto) {
        TagDto createdTag = tagService.createTag(dto);
        return ResponseEntity.ok(createdTag);
    }

    @Operation(summary = "모든 태그 조회", description = "모든 태그를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags() {
        List<TagDto> tags = tagService.getAllTags();
        return ResponseEntity.ok(tags);
    }

    @Operation(summary = "태그 이름으로 검색", description = "태그 이름으로 태그를 검색합니다.")
    @GetMapping("/{name}")
    public ResponseEntity<TagDto> getTagByName(@PathVariable String name) {
        TagDto tagDto = tagService.getTagByName(name);
        return ResponseEntity.ok(tagDto);
    }
}
