package com.blogapp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Post {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_title", length = 100, nullable = false)
    private String title;

    @Column(name = "post_content", length = 10000, nullable = false)
    private String content;

    @Column(name = "post_image", length = 100, nullable = false)
    private String imageName;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Catg category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Usr user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Cmnt> comments = new HashSet<>();

    @CreationTimestamp
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "submitted_date")
    private LocalDateTime submittedDate;

    @UpdateTimestamp
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;
}