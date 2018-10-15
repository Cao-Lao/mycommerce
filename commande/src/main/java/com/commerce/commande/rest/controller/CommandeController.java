package com.commerce.commande.rest.controller;

import com.commerce.commande.business.service.CommandeService;
import com.commerce.commande.persistence.model.Commande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CommandeController {

    private CommandeService commandeService;

    @Autowired
    public void setCommandeService(final CommandeService commandeService) {

        this.commandeService = commandeService;
    }

    @GetMapping(value = "/commande/{id}")
    public Commande lookForOneCommande(@PathVariable final Long id) {

        return this.commandeService.getCommande(id);
    }

    @PostMapping(value = "/commande")
    public ResponseEntity<Commande> addCommande(@Valid @RequestBody final Commande commande) {

        return new ResponseEntity<>(this.commandeService.postCommande(commande), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/commande")
    public ResponseEntity<Commande> updateCommande(@Valid @RequestBody final Commande commande) {

        return new ResponseEntity<>(this.commandeService.patchCommande(commande), HttpStatus.CREATED);
    }
}
