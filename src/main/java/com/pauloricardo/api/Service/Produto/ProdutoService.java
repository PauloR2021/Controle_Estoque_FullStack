package com.pauloricardo.api.Service.Produto;


import com.pauloricardo.api.DTO.Produto.ProdutoRequestDTO;
import com.pauloricardo.api.DTO.Produto.ProdutoResponseDTO;
import com.pauloricardo.api.Exception.BussinessException;
import com.pauloricardo.api.Exception.CodigoBarrasDuplicadoException;
import com.pauloricardo.api.Exception.GlobalExceptionHandler;
import com.pauloricardo.api.Exception.ResourceNotFoundException;
import com.pauloricardo.api.Model.Produto.ProdutoModel;
import com.pauloricardo.api.Respository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Transactional
    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO dto){

        ProdutoModel produto = new ProdutoModel();

        if(dto.nome() == null||dto.nome().isBlank()){
            throw new BussinessException(
                    "Nome precisa ser preenchido",
                    HttpStatus.BAD_REQUEST
            );
        }


        if(dto.codigoBarras() == null||dto.codigoBarras().isBlank() ){
            throw new BussinessException(
                    "Codigo de Barras precisa ser preenchido",
                    HttpStatus.BAD_REQUEST
            );
        }

        if(dto.quantidade() <0){
            throw new BussinessException(
                    "Quantidade não pode ser menor que 0",
                    HttpStatus.BAD_REQUEST
            );
        }

        if(dto.quantidadeMinima() < 2){
            throw new BussinessException(
                    "Quantidade Minima não pode ser menor que 2",
                    HttpStatus.BAD_REQUEST
            );

        }
        if (produtoRepository.existsByCodigoBarras(produto.getCodigoBarras())) {
            throw new CodigoBarrasDuplicadoException();
        }



        produto.setNome(dto.nome());
        produto.setCodigoBarras(dto.codigoBarras());
        produto.setQuantidade(dto.quantidade());
        produto.setPreco(dto.preco());
        produto.setEstoqueMinimo(dto.quantidadeMinima());
        produto.setAtivo(dto.ativo());

        LocalDateTime data = LocalDateTime.now();

        produto.setCreatedAt(data);

        produtoRepository.save(produto);

        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getCodigoBarras(),
                produto.getQuantidade(),
                produto.getEstoqueMinimo(),
                produto.getPreco(),
                produto.getAtivo(),
                produto.getCreatedAt()
        );


    }

    @Transactional
    public List<ProdutoResponseDTO> findByAll(){
        return produtoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();

    }

    @Transactional
    public ProdutoResponseDTO editarProduto (Integer id, ProdutoRequestDTO dto){

        ProdutoModel produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID não existe"));

        if (dto.nome() == null || dto.nome().isBlank()) {
            throw new BussinessException("Nome precisa ser preenchido", HttpStatus.BAD_REQUEST);
        }

        if (dto.codigoBarras() == null || dto.codigoBarras().isBlank()) {
            throw new BussinessException("Código de barras precisa ser preenchido", HttpStatus.BAD_REQUEST);
        }

        if (dto.quantidade() < 0) {
            throw new BussinessException("Quantidade não pode ser menor que 0", HttpStatus.BAD_REQUEST);
        }

        if (dto.quantidadeMinima() < 2) {
            throw new BussinessException("Quantidade mínima não pode ser menor que 2", HttpStatus.BAD_REQUEST);
        }

        // 🔥 REGRA CORRETA
        if (produtoRepository.existsByCodigoBarrasAndIdNot(dto.codigoBarras(), id)) {
            throw new CodigoBarrasDuplicadoException();
        }

        produto.setNome(dto.nome());
        produto.setCodigoBarras(dto.codigoBarras());
        produto.setPreco(dto.preco());
        produto.setQuantidade(dto.quantidade());
        produto.setEstoqueMinimo(dto.quantidadeMinima());
        produto.setAtivo(dto.ativo());

        return toResponse(produtoRepository.save(produto));

    }

    @Transactional
    public void excluirProduto(Integer id){
        ProdutoModel produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID Não Existe"));

        produtoRepository.delete(produto);
    }


    private ProdutoResponseDTO toResponse(ProdutoModel produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getCodigoBarras(),
                produto.getQuantidade(),
                produto.getEstoqueMinimo(),
                produto.getPreco(),
                produto.getAtivo(),
                produto.getCreatedAt()
        );
    }



}
