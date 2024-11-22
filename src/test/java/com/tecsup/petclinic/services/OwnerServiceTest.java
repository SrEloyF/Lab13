package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exception.OwnerNotFoundException;
import com.tecsup.petclinic.repositories.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    public void testFindOwnerById() {
        String FIRST_NAME = "George";
        String LAST_NAME = "Franklin";
        Owner owner = new Owner();
        owner.setFirstName(FIRST_NAME);
        owner.setLastName(LAST_NAME);
        Owner savedOwner = ownerService.create(owner);
        Integer ID = savedOwner.getId();
        Owner foundOwner = null;
        try {
            foundOwner = ownerService.findById(ID);
        } catch (OwnerNotFoundException e) {
            fail(e.getMessage());
        }
        log.info("Owner found: " + foundOwner);
        assertEquals(FIRST_NAME, foundOwner.getFirstName());
        assertEquals(LAST_NAME, foundOwner.getLastName());
    }

    @Test
    public void testFindOwnerByLastName() {
        String LAST_NAME = "Eloy";
        int SIZE_EXPECTED = 1;
        Owner owner = new Owner();
        owner.setFirstName("Sanchez");
        owner.setLastName(LAST_NAME);
        ownerService.create(owner);
        List<Owner> owners = ownerService.findByLastName(LAST_NAME);
        assertEquals(SIZE_EXPECTED, owners.size());
    }

    @Test
    public void testCreateOwner() {
        String FIRST_NAME = "Kanye";
        String LAST_NAME = "West";
        Owner owner = new Owner();
        owner.setFirstName(FIRST_NAME);
        owner.setLastName(LAST_NAME);
        Owner createdOwner = ownerService.create(owner);
        log.info("Owner created: " + createdOwner);
        assertNotNull(createdOwner.getId());
        assertEquals(FIRST_NAME, createdOwner.getFirstName());
        assertEquals(LAST_NAME, createdOwner.getLastName());
    }

    @Test
    public void testUpdateOwner() {
        String FIRST_NAME = "Kanye";
        String LAST_NAME = "West";
        String UPDATED_FIRST_NAME = "Kanye2";
        String UPDATED_LAST_NAME = "West2";
        Owner owner = new Owner();
        owner.setFirstName(FIRST_NAME);
        owner.setLastName(LAST_NAME);
        Owner createdOwner = ownerService.create(owner);
        log.info("Owner created: " + createdOwner);
        createdOwner.setFirstName(UPDATED_FIRST_NAME);
        createdOwner.setLastName(UPDATED_LAST_NAME);
        Owner updatedOwner = ownerService.update(createdOwner);
        log.info("Updated owner: " + updatedOwner);
        assertEquals(UPDATED_FIRST_NAME, updatedOwner.getFirstName());
        assertEquals(UPDATED_LAST_NAME, updatedOwner.getLastName());
    }

    @Test
    public void testDeleteOwner() {
        String FIRST_NAME = "Maria";
        String LAST_NAME = "Escobito";
        Owner owner = new Owner();
        owner.setFirstName(FIRST_NAME);
        owner.setLastName(LAST_NAME);
        Owner createdOwner = ownerService.create(owner);
        log.info("Owner created: " + createdOwner);
        try {
            ownerService.delete(createdOwner.getId());
        } catch (OwnerNotFoundException e) {
            fail(e.getMessage());
        }
        try {
            ownerService.findById(createdOwner.getId());
            fail("Owner should not be found after deletion");
        } catch (OwnerNotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testFindAllOwners() {
        String FIRST_NAME = "Jean";
        String LAST_NAME = "Coleman";
        Owner owner = new Owner();
        owner.setFirstName(FIRST_NAME);
        owner.setLastName(LAST_NAME);
        ownerService.create(owner);
        List<Owner> owners = ownerService.findAll();
        assertTrue(owners.size() > 0);
    }
}
