package org.blogg.Service;

import org.blogg.Payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    public CategoryDto createCategory(CategoryDto categoryDto);
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
    public void deleteCategory(Integer categoryId);
    public CategoryDto findById(Integer categoryId);
    public List<CategoryDto> viewAllCategory();
}
