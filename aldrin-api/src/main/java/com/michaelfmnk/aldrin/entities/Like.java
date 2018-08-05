package com.michaelfmnk.aldrin.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "likes")
public class Like {

    @EmbeddedId
    private LikePK likePK;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class LikePK implements Serializable {
        @Column(name = "user_id")
        private Integer userId;
        @Column(name = "post_id")
        private Integer postId;
    }
}
