package br.com.imaster7.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.imaster7.model.MesAnoIdComposto;
import br.com.imaster7.model.MesAnoTitulo;

public interface MesesAnosTitulos extends JpaRepository<MesAnoTitulo, MesAnoIdComposto> {

}
