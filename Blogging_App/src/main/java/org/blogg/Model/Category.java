package org.blogg.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer categoryId;
    String categoryTitle;
    String categoryDescription;
    

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    List<Post> postList = new ArrayList<>();


	public Integer getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}


	public String getCategoryTitle() {
		return categoryTitle;
	}


	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}


	public String getCategoryDescription() {
		return categoryDescription;
	}


	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}


	public List<Post> getPostList() {
		return postList;
	}


	public void setPostList(List<Post> postList) {
		this.postList = postList;
	}

	
    
}
