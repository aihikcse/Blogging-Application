package org.blogg.Payloads;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Integer categoryId;
        
        @NotBlank
        @Size(min=4,message="Minimum size of Category title is 4 !")
        String categoryTitle;
        
        @NotBlank
        @Size(min=10,message="Minimum size of Category desc is 10 !")
        String categoryDescription;
        
        
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
		
        
        
        
        
}
