package com.mycommerce.produit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycommerce.produit.model.Produit;

@Repository
public interface ProduitDao extends JpaRepository<Produit, Long> {
}