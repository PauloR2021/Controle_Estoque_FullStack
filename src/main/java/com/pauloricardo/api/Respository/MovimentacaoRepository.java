package com.pauloricardo.api.Respository;

import com.pauloricardo.api.Model.Movimentacao.MovimentacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<MovimentacaoModel, Integer> {
}
