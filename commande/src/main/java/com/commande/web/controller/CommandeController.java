package com.commande.web.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.commande.dao.CommandeDao;
import com.commande.model.Commande;
import com.commande.web.exception.CommandeIntrouvableException;
import com.commande.web.exception.CommandeNonAjoutableException;

@RestController
public class CommandeController {

	@Autowired
	CommandeDao commandeDao;

	@PostMapping(value = "/commande")
	public ResponseEntity<Commande> ajouterCommande(@RequestBody final Commande commande) {

		final Commande nouvelleCommande = this.commandeDao.save(commande);

		if (nouvelleCommande.equals(null)) {
			throw new CommandeNonAjoutableException(new String("Impossible d'ajouter cette commande"));
		}

		return new ResponseEntity<>(commande, HttpStatus.CREATED);
	}

	@GetMapping(value = "/commande/{id}")
	public Optional<Commande> recupererUneCommande(@PathVariable final Long id) {

		final Optional<Commande> commande = this.commandeDao.findById(id);

		if (!commande.isPresent()) {
			throw new CommandeIntrouvableException(new String("Cette commande n'existe pas"));
		}

		return commande;
	}

	/*
	 * Permet de mettre à jour une commande existante.
	 * save() mettra à jours uniquement les champs renseignés dans l'objet commande reçu. Ainsi dans ce cas, comme le champs date dans "commande"
	 * n'est
	 * pas renseigné, la date précédemment enregistrée restera en place
	 **/
	@PutMapping(value = "/commande")
	public void updateCommande(@RequestBody final Commande commande) {

		this.commandeDao.save(commande);
	}
}
