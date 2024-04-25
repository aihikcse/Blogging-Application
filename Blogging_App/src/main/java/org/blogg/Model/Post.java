package org.blogg.Model;
import lombok.*;
import jakarta.persistence.*;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Column(nullable = false, length = 10000)
	private String content;
	
	private String imageName;
	
	private Date addedDate;

	@ManyToOne
	@JoinColumn(name = "UserId")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
//	@JoinColumn(name = "pc_fid", referencedColumnName = "postId")
	Set<Comment> comments = new HashSet<>();

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	

		
	
	}
