package org.kenux.springjpa.jpashop.service;

import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kenux.springjpa.jpashop.domain.Album;
import org.kenux.springjpa.jpashop.domain.Item;
import org.kenux.springjpa.jpashop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Slf4j
public class ItemServiceTest {
    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    /*
    @Test
    public void 상품_저장_테스트() {
        // Given
        Item album = new Album();
        album.setName("앨범 상품");

        // When
        Long saveId = itemService.saveItem(album);

        // Then
        assertEquals(album, itemRepository.findOne(saveId));
    }*/
}
