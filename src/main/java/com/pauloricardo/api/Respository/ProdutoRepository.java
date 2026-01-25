package com.pauloricardo.api.Respository;

import com.pauloricardo.api.Model.Produto.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, Integer> {

    boolean existsByCodigoBarras(String codigoBarras);

    boolean existsByCodigoBarrasAndIdNot(String s, Integer id);
}
