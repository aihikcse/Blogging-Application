package org.blogg.Service.Impl;

import org.blogg.Exception.RecourseNotFoundException;
import org.blogg.Model.Category;
import org.blogg.Payloads.CategoryDto;
import org.blogg.Repository.CategoryRepository;
import org.blogg.Service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category createdCategory = categoryRepository.save(category);
        CategoryDto savedCategory = modelMapper.map(createdCategory, CategoryDto.class);
        return savedCategory;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(()->
                new RecourseNotFoundException("category", "categoryId", categoryId));
        existingCategory.setCategoryTitle(categoryDto.getCategoryTitle());
        existingCategory.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = categoryRepository.save(existingCategory);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(()->
                new RecourseNotFoundException("category", "categoryId", categoryId));
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public CategoryDto findById(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->
                new RecourseNotFoundException("category", "categoryId", categoryId));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> viewAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category-> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }
}
