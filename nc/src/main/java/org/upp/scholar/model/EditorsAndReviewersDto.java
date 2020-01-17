package org.upp.scholar.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditorsAndReviewersDto {
    private String issn;
    private FormSubmissionDto editors;
    private FormSubmissionDto reviewers;
}
