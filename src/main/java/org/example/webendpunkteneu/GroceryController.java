package org.example.webendpunkteneu;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"localhost", "https://projekte.tgm.ac.at"}, exposedHeaders = {"Access-Control-Allow-Origin"})
public class GroceryController {

    private GroceryListService mock;

    public GroceryController() {
        this.mock = new GroceryListServiceMock();
    }

    @GetMapping(value = "/api/grocery/{id}", produces = "application/json")
    public ResponseEntity<GroceryItemDto> getItem(@PathVariable Long id) {

        GroceryItemDto groceryItemDto = mock.getGroceryItem(id).get();

        return new ResponseEntity<>(groceryItemDto, HttpStatus.OK);
    }

    @GetMapping(value = "/api/grocery", produces = "application/json")
    public ResponseEntity<List<GroceryItemDto>> getAllItems() {

        List<GroceryItemDto> groceryItems = mock.getGroceryItems();

        if (groceryItems.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(groceryItems, HttpStatus.OK);
    }


    @DeleteMapping(value = {"/api/grocery", "/api/grocery/{id}"}, produces = "application/json")
    public ResponseEntity<String> deleteItem(@PathVariable(required = false) Long id) {

        if (id == null) {
            List<GroceryItemDto> groceryItems = mock.getGroceryItems();

            if (groceryItems.isEmpty()) {
                return new ResponseEntity<>("Keine Items zum Löschen vorhanden", HttpStatus.NO_CONTENT);
            }
            mock.deleteGroceryItem(null);
            return new ResponseEntity<>("Alle Items gelöscht!", HttpStatus.OK);
        } else {
            mock.deleteGroceryItem(id);
            return new ResponseEntity<>("Item " + id + " gelöscht!", HttpStatus.OK);
        }
    }


    @PutMapping(value = "/api/grocery", produces = "application/json")
    public ResponseEntity<GroceryItemDto> itemEditieren(@Valid @RequestBody GroceryItemDto item) {

        item.correctErrors();

        GroceryItemDto groceryItemDto = new GroceryListServiceMock().updateGroceryItem(item);

        return new ResponseEntity<>(groceryItemDto, HttpStatus.OK);
    }

    @PostMapping(value = "/api/grocery", produces = "application/json")
    public ResponseEntity<GroceryItemDto> itemHinzufuegen(@Valid @RequestBody GroceryItemDto item) {

        if(item.getId() != null) throw new IllegalArgumentException("ID darf nicht gesetzt sein!");

        GroceryItemDto groceryItem = new GroceryListServiceMock().createGroceryItem(item);

        return new ResponseEntity<>(groceryItem, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/api/grocery/{id}", produces = "application/json")
    public ResponseEntity<GroceryItemDto> itemCollecten(@PathVariable Long id, @RequestParam boolean collected) {

        GroceryItemDto groceryItemDto = new GroceryListServiceMock().patchGroceryItem(id, collected);

        return new ResponseEntity<>(groceryItemDto, HttpStatus.OK);
    }
}
