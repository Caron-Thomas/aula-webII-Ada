package br.com.ada.programacaowebii.aula.repository;

import br.com.ada.programacaowebii.aula.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    public List<Cliente> findByNomeContaining(String nome);

    public List<Cliente> findByNomeContainingOrDataNascimentoOrderByNomeAsc(String nome, LocalDate dataNascimento);

    public List<Cliente> findByDataNascimentoBetween(LocalDate dataInicial, LocalDate dataFinal);

    public Optional<Cliente> findByCpf(String cpf);

    public void deleteByCpf(String cpf);

    //neste caso o Cliente é a Entidade e nao o nome da tabela no banco de dados
    @Query("SELECT c FROM Cliente c WHERE c.cpf = ?1") // depois do "FROM" é pelo nome da classe, não da tabela.
    public Optional<Cliente> buscarClientePorCpfParametroIndexado(String cpf);
    //Pela forma indexado, vc manda pela ordem de parametros. Ex.: ?0 -> depois ?1 e assim por diante. Como o
    // cpf é o 2° parametro da tabela, então é ?1 (0, 1, 2, )

    @Query("SELECT c FROM Cliente c WHERE c.cpf = :cpf")
    public Optional<Cliente> buscarClientePorCpfParametroNominal(@Param("cpf") String cpf);

    @Query("SELECT c FROM Cliente c WHERE c.dataNascimento BETWEEN ?1 AND ?2")
    public List<Cliente> buscarClienteEntreDatasNascimentoParametroIndexado(LocalDate dataInicial, LocalDate dataFinal);

    @Query("SELECT c FROM Cliente c WHERE c.dataNascimento BETWEEN :dataInicial AND :dataFinal")
    public List<Cliente> buscarClienteEntreDatasNascimentoNominal(@Param("dataInicial") LocalDate dataInicial,
                                                                  @Param("dataFinal") LocalDate dataFinal);

}
