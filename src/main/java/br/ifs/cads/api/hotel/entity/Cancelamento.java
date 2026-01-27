package br.ifs.cads.api.hotel.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cancelamentos")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"reserva"})
public class Cancelamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

    @Column(name = "data_cancelamento", nullable = false)
    private LocalDateTime dataCancelamento;

    @Column(name = "motivo", length = 500, nullable = false)
    private String motivo;

    @Column(name = "valor_multa")
    private Double valorMulta;

    @Column(name = "justificativa", length = 500)
    private String justificativa;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    public Cancelamento(Reserva reserva, String motivo) {
        this.reserva = reserva;
        this.motivo = motivo;
        this.dataCancelamento = LocalDateTime.now();
        this.ativo = true;
    }

    public Cancelamento(Reserva reserva, String motivo, Double valorMulta) {
        this.reserva = reserva;
        this.motivo = motivo;
        this.valorMulta = valorMulta;
        this.dataCancelamento = LocalDateTime.now();
        this.ativo = true;
    }

    public Cancelamento(Reserva reserva, String motivo, Double valorMulta, String justificativa) {
        this.reserva = reserva;
        this.motivo = motivo;
        this.valorMulta = valorMulta;
        this.justificativa = justificativa;
        this.dataCancelamento = LocalDateTime.now();
        this.ativo = true;
    }
}
