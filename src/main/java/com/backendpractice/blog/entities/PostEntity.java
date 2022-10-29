package com.backendpractice.blog.entities;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class PostEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_id", nullable = false)
  private Long id;

  @Column(name = "title", nullable = false, unique = true, length = 100)
  private String title;

  @Column(name = "content", length = 10000)
  private String content;

  @Column(name = "image_name")
  private String imageName;

  @Temporal(TemporalType.DATE)
  @Column(name = "added_date")
  private Date addedDate;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private CategoryEntity category;

  @ManyToOne
  @JoinColumn(name = "profile_id")
  private ProfileEntity profile;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CommentEntity> comments = new LinkedHashSet<>();

}
