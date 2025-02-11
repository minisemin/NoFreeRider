package com.teamnine.noFreeRider.project.domain;

import com.teamnine.noFreeRider.member.domain.Member;
import com.teamnine.noFreeRider.project.dto.UpdateProjectDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "projects")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Project {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "project_id", updatable = false)
    private UUID project_id;

    @Column(name = "project_name")
    private String project_name;

    @Column(name = "project_summary")
    private String project_summary;

    @Column(name = "status_code")
    private int status_code; // 0 : 시작, 1 : 진행, 2 : 중단, 3: 완료

    @ManyToOne
    @JoinColumn(name = "leader_id", referencedColumnName = "member_id", updatable = false)
    private Member leader;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "ended_at")
    private LocalDateTime ended_at;

    @Builder
    public Project(String project_name, String project_summary, Member leader) {
        this.project_name = project_name;
        this.project_summary = project_summary;
        this.status_code = 0;
        this.leader = leader;
    }

    public void updateLeaderNo(Member nLeader) {
        this.leader = nLeader;
    }
    public void updateNameAndSummary(UpdateProjectDto dto) {
        this.project_name = dto.name();
        this.project_summary = dto.summary();
    }

    public void updateStatusCode(int newCode) {
        this.status_code = newCode;
    }
}
