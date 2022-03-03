package jpabook.jpashop.controller;


import lombok.Getter;
import lombok.Setter;

// DTO
@Getter @Setter
public class BookForm {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String isbn;
    private String author;



}
