package org.upp.scholar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto implements Serializable {
    private String reviewer;
    private String commentAuthor;
    private String commentEditor;
    private String recommendation;
}
