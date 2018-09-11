package com.paiement.proxy;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.paiement.bean.CommandeBean;

@FeignClient(name = "commande", url = "localhost:9002")
public interface CommandeProxy {

	@PutMapping(value = "/commande")
	void updateCommande(@RequestBody CommandeBean commande);

	@GetMapping(value = "/commande/{id}")
	Optional<CommandeBean> recupererUneCommande(@PathVariable(value = "id") Long id);
}