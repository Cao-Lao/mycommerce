package com.mycommerce.paiement.rest.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mycommerce.paiement.business.binder.bean.CommandeBean;
import com.mycommerce.paiement.business.binder.proxy.CommandeProxy;
import com.mycommerce.paiement.business.exception.PaiementExistantException;
import com.mycommerce.paiement.business.exception.PaiementImpossibleException;
import com.mycommerce.paiement.persistence.dao.PaiementDao;
import com.mycommerce.paiement.persistence.model.Paiement;

@RestController
public class PaiementController {

	private static final Logger LOG = LoggerFactory.getLogger(PaiementController.class);

	private PaiementDao		paiementDao;
	private CommandeProxy	commandeProxy;

	public PaiementController() {

	}

	@Autowired
	public void setPaiementDao(final PaiementDao paiementDao) {

		this.paiementDao = paiementDao;
	}

	@Autowired
	public void setCommandeProxy(final CommandeProxy commandeProxy) {

		this.commandeProxy = commandeProxy;
	}

	/*
	 * Opération pour enregistrer un paiement et notifier le microservice commandes pour mettre à jour le statut de la commande en question
	 **/
	@PostMapping(value = "/paiement")
	public ResponseEntity<Paiement> payerUneCommande(@RequestBody final Paiement paiement) {

		PaiementController.LOG.info("**** using {}", this.getClass());

		final Paiement paiementRecupere = this.paiementDao.findByidCommande(paiement.getIdCommande());

		if (paiementRecupere.equals(null)) {
			throw new PaiementExistantException("Cette commande est déjà payée");
		}

		final Paiement paiementAjoutee = this.paiementDao.save(paiement);

		if (paiementAjoutee.equals(null)) {
			throw new PaiementImpossibleException("Erreur, impossible d'établir le paiement, réessayez plus tard");
		}

		final Optional<CommandeBean> commandeRecuperee = this.commandeProxy
				.recupererUneCommande(paiement.getIdCommande());

		commandeRecuperee.get().setEstPayee(true);

		this.commandeProxy.updateCommande(commandeRecuperee.get());

		return new ResponseEntity<>(paiementAjoutee, HttpStatus.CREATED);
	}
}
