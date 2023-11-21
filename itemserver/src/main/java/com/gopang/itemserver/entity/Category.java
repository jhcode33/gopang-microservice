package com.gopang.itemserver.entity;

import com.gopang.itemserver.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Entity
@Table(name = "category")
@NoArgsConstructor
public class Category extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private Category parent;

    @Column(name = "depth")
    private Long depth;

    @Builder.Default
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();

    @Builder
    public Category(Long categoryId, String name, Category parent, Long depth, List<Category> child, List<Item> items) {
        this.categoryId = categoryId;
        this.name = name;
        this.parent = parent;
        this.depth = depth;
        this.child = child;
        this.items = items;
    }

    //== 양방향 연관관계 편의 메서드 ==//
    public void addItem(Item item) {
        items.add(item);
        item.setCategory(this); // 양방향 연관관계 설정
    }

    public Long setParent(Category newParent) {
        // 현재 객체가 부모가 없는 경우, 새로운 연관관계를 설정
        if (this.parent == null) {
            this.parent = newParent;
            newParent.getChild().add(this);
            return newParent.getCategoryId();
        }

        // 현재 객체가 이미 부모가 있는 경우
        // 기존 부모와 새로운 부모가 다를 경우에만 처리
        if (!this.parent.equals(newParent)) {
            Category oldParent = this.parent;
            this.parent = null;
            oldParent.getChild().remove(this);

            // 새로운 부모와 연관관계 설정
            this.parent = newParent;
            newParent.getChild().add(this);
            return newParent.getCategoryId();
        }
        return this.parent.getCategoryId();
    }


    public void update(String name, Long depth) {
        this.name = name;
        this.depth = depth;
    }
}
