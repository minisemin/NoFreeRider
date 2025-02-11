package com.teamnine.noFreeRider.project.service;

import com.teamnine.noFreeRider.member.domain.Member;
import com.teamnine.noFreeRider.member.repository.MemberRepository;
import com.teamnine.noFreeRider.member.domain.MemberProject;
import com.teamnine.noFreeRider.member.repository.MemberProjectRepository;
import com.teamnine.noFreeRider.project.domain.Project;
import com.teamnine.noFreeRider.project.dto.*;
import com.teamnine.noFreeRider.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final MemberProjectRepository memberProjectRepository;

    public Project save(AddProjectDto addProjectDto) {
        return projectRepository.save(addProjectDto.toEntity());
    }

    @Transactional
    public Project changeLeader(ChangeProjectLeaderDto dto, UUID projectID) throws Exception {
        Project project = projectRepository.findById(projectID)
                .orElseThrow(IllegalArgumentException::new);

        if (!project.getLeader().getMember_id().equals(dto.exLeader_id())) {
            throw new IllegalArgumentException();
        }

        Member nLeader = memberRepository.findById(dto.newLeader_id())
                .orElseThrow(IllegalArgumentException::new);

        project.updateLeaderNo(nLeader);

        return project;
    }

    public Project addMember(MemberProjectDto dto) {
        Project project = projectRepository.findById(dto.project_id())
                .orElseThrow(IllegalArgumentException::new);

        if (memberProjectRepository.existsByMember_idAndProject_id(dto.member_id(), dto.project_id())) {
            throw new IllegalArgumentException();
        }

        memberProjectRepository.save(MemberProject.builder()
                .member(memberRepository.findById(dto.member_id())
                        .orElseThrow(IllegalArgumentException::new))
                .project(project)
                .build());

        return project;
    }

    public Project changeStatusCode() {

    }


    public boolean isProjectLeader(MemberProjectDto dto) {
        Optional<UUID> leaderUUID = projectRepository.findLeader_idByProject_id(dto.project_id());
        if (leaderUUID.isEmpty()) {
            return false;
        }
        return leaderUUID.get().equals(dto.member_id());
    }

    public Project update(UpdateProjectDto dto) {
        Project project = projectRepository.findById(dto.project_id())
                .orElseThrow(IllegalArgumentException::new);
        project.updateNameAndSummary(dto);
        return project;
    }
}
