package org.kenux.springjpa.jpashop.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.kenux.springjpa.jpashop.domain.Book;
import org.kenux.springjpa.jpashop.domain.Item;
import org.kenux.springjpa.jpashop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@Slf4j
public class ItemController {

    @Autowired
    ItemService itemService;

    @PostMapping("/new")
    public ResponseEntity<List<Item>> createItem(@RequestBody Book item) {
        log.info("book item = " + item);
        itemService.saveItem(item);
        return ResponseEntity.ok(getItems());
    }

    @GetMapping("/")
    public ResponseEntity<List<Item>> list() {
        return ResponseEntity.ok(getItems());
    }

    @PutMapping("/edit")
    public ResponseEntity<Item> updateItem(@RequestBody Book item) {
        itemService.saveItem(item);
        return ResponseEntity.ok(itemService.findOne(item.getId()));
    }

    private List<Item> getItems() {
        return itemService.findItems();
    }
}
