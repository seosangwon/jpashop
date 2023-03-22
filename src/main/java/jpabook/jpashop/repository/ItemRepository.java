package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em; // 스프링이 기본적으로 해줄 것이다라는데, 지금은 없어도 스프링이 자동으로 해주는가?

    public void save(Item item){
        if(item.getId()==null){
            em.persist(item); //save
        }else{
            em.merge(item);  // 또는 update
        }

    }

    public Item findOne(Long id){
        return em.find(Item.class,id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i",Item.class).getResultList();
    }

}
