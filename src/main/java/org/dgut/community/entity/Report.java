package org.dgut.community.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long articleId;

    @Column(nullable = false)
    private Long reporterId;

    @Column(nullable = false)
    private String reportReason;

    @Column(nullable = false)
    private int reprtManage;
}
