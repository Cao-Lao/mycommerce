package com.mycommerce.paiement.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mycommerce.paiement.business.service.PaiementService;
import com.mycommerce.paiement.persistence.model.Paiement;

@RestController
public class PaiementController {

	private static final Logger LOG = LoggerFactory.getLogger(PaiementController.class);

	@Autowired
	private PaiementService paiementService;

	/*
	 * Opération pour enregistrer un paiement et notifier le microservice commandes pour mettre à jour le statut de la commande en question
	 **/
	@PostMapping(value = "/paiement")
	public ResponseEntity<Paiement> payerUneCommande(@RequestBody final Paiement paiement) {

		PaiementController.LOG.info("**** using {}", this.getClass().getSimpleName());

		return new ResponseEntity<>(this.paiementService.postPaiement(paiement), HttpStatus.CREATED);
	}
}
