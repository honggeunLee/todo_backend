package org.example.todo_backend.service;

import lombok.RequiredArgsConstructor;
import org.example.todo_backend.dto.TagCreateRequestDto;
import org.example.todo_backend.dto.TagDto;
import org.example.todo_backend.entity.Tag;
import org.example.todo_backend.repository.TagRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    // 새로운 태그 생성
    @Transactional
    public TagDto createTag(TagCreateRequestDto dto) {
        try {
            Tag tag = new Tag();
            tag.setName(dto.getName());
            Tag savedTag = tagRepository.save(tag);
            return convertToDto(savedTag);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("이미 존재하는 태그입니다: " + dto.getName());
        }
    }

    // 모든 태그 조회
    public List<TagDto> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // 태그 이름으로 조회
    public TagDto getTagByName(String name) {
        Tag tag = tagRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("태그를 찾을 수 없습니다: " + name));

        return convertToDto(tag);
    }

    // Tag DTO 변환 메서드
    public TagDto convertToDto(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
        return tagDto;
    }
}
